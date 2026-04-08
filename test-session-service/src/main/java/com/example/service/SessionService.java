package com.example.service;

import com.example.dto.SessionDto;
import com.example.dto.SessionResultDto;
import com.example.dto.StartSessionRequest;
import com.example.entity.SessionStatus;
import com.example.entity.TestSession;
import com.example.producer.SessionEventProducer;
import com.example.repository.AnswerRepository;
import com.example.repository.TestSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final TestSessionRepository sessionRepository;
    private final AnswerRepository answerRepository;
    private final SessionEventProducer eventProducer;
    private final AnswerService answerService;

    @Value("${session.default-time-limit:60}")
    private int defaultTimeLimit;

    @Transactional
    public SessionDto startSession(StartSessionRequest request) {
        // Проверяем, нет ли активной сессии у пользователя по этому тесту
        boolean hasActiveSession = sessionRepository.existsByUserIdAndTestIdAndStatus(
                request.getUserId(), request.getTestId(), SessionStatus.ACTIVE);

        if (hasActiveSession) {
            throw new RuntimeException("Active session already exists for this test and user");
        }

        // TODO: Получить информацию о тесте из test-management-service
        // Пока используем значения по умолчанию
        int timeLimitMinutes = defaultTimeLimit;

        TestSession session = new TestSession();
        session.setTestId(request.getTestId());
        session.setUserId(request.getUserId());
        session.setTimeLimitMinutes(timeLimitMinutes);
        session.setStartedAt(LocalDateTime.now());
        session.setStatus(SessionStatus.ACTIVE);

        session = sessionRepository.save(session);

        // Отправляем событие в Kafka
        eventProducer.sendSessionStartedEvent(session.getId(), session.getUserId(), session.getTestId());

        log.info("Started session: {} for user: {}, test: {}", session.getId(), session.getUserId(), session.getTestId());

        return convertToDto(session);
    }

    @Transactional
    public SessionDto completeSession(Long sessionId) {
        TestSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (session.getStatus() != SessionStatus.ACTIVE) {
            throw new RuntimeException("Session is not active");
        }

        session.setStatus(SessionStatus.COMPLETED);
        session.setCompletedAt(LocalDateTime.now());
        session = sessionRepository.save(session);

        // Подсчитываем результаты
        SessionResultDto result = answerService.calculateResults(sessionId);

        // Отправляем событие в Kafka
        eventProducer.sendSessionCompletedEvent(
                sessionId,
                session.getUserId(),
                session.getTestId(),
                result.getTotalPoints(),
                result.getTotalQuestions(),
                result.getCorrectAnswers()
        );

        log.info("Completed session: {}", sessionId);
        return convertToDto(session);
    }

    @Transactional
    public void expireSession(Long sessionId) {
        sessionRepository.updateStatus(sessionId, SessionStatus.EXPIRED);
        log.info("Expired session: {}", sessionId);
    }

    @Scheduled(fixedDelay = 60000) // Каждую минуту
    @Transactional
    public void expireInactiveSessions() {
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(30);
        List<TestSession> expiredSessions = sessionRepository.findByStatusAndStartedAtBefore(
                SessionStatus.ACTIVE, expiryTime);

        for (TestSession session : expiredSessions) {
            expireSession(session.getId());
        }

        if (!expiredSessions.isEmpty()) {
            log.info("Expired {} inactive sessions", expiredSessions.size());
        }
    }

    public SessionDto getSessionById(Long id) {
        TestSession session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        return convertToDto(session);
    }

    public List<SessionDto> getSessionsByUser(Long userId) {
        return sessionRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SessionDto> getActiveSessionsByUser(Long userId) {
        return sessionRepository.findByUserIdAndStatus(userId, SessionStatus.ACTIVE).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SessionResultDto getSessionResults(Long sessionId) {
        return answerService.calculateResults(sessionId);
    }

    private SessionDto convertToDto(TestSession session) {
        SessionDto dto = new SessionDto();
        dto.setId(session.getId());
        dto.setTestId(session.getTestId());
        dto.setUserId(session.getUserId());
        dto.setStartedAt(session.getStartedAt());
        dto.setCompletedAt(session.getCompletedAt());
        dto.setTimeLimitMinutes(session.getTimeLimitMinutes());
        dto.setStatus(session.getStatus().name());

        long totalAnswers = answerRepository.countBySessionId(session.getId());
        dto.setTotalAnswers((int) totalAnswers);

        return dto;
    }
}
