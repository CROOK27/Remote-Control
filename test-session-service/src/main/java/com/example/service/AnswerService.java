package com.example.service;

import com.example.dto.AnswerDto;
import com.example.dto.AnswerRequest;
import com.example.dto.SessionResultDto;
import com.example.entity.Answer;
import com.example.entity.SessionStatus;
import com.example.entity.TestSession;
import com.example.producer.SessionEventProducer;
import com.example.repository.AnswerRepository;
import com.example.repository.QuestionSnapshotRepository;
import com.example.repository.TestSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final TestSessionRepository sessionRepository;
    private final SessionEventProducer eventProducer;


    @Transactional
    public AnswerDto saveAnswer(Long sessionId, AnswerRequest request) {
        TestSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (session.getStatus() != SessionStatus.ACTIVE) {
            throw new RuntimeException("Session is not active");
        }

        // TODO: Проверить правильность ответа через test-management-service
        boolean isCorrect = checkAnswer(request.getQuestionId(), request.getAnswer());
        int pointsEarned = isCorrect ? 10 : 0;

        // Проверяем, не отвечал ли уже на этот вопрос
        Answer existingAnswer = answerRepository.findBySessionIdAndQuestionId(sessionId, request.getQuestionId())
                .orElse(null);

        Answer answer;
        if (existingAnswer != null) {
            // Обновляем существующий ответ
            existingAnswer.setAnswer(request.getAnswer());
            existingAnswer.setIsCorrect(isCorrect);
            existingAnswer.setPointsEarned(pointsEarned);
            existingAnswer.setAnsweredAt(LocalDateTime.now());
            answer = answerRepository.save(existingAnswer);
            log.info("Updated answer for session: {}, question: {}", sessionId, request.getQuestionId());
        } else {
            // Создаём новый ответ
            answer = new Answer();
            answer.setSession(session);
            answer.setQuestionId(request.getQuestionId());
            answer.setAnswer(request.getAnswer());
            answer.setIsCorrect(isCorrect);
            answer.setPointsEarned(pointsEarned);
            answer = answerRepository.save(answer);
            log.info("Saved answer for session: {}, question: {}", sessionId, request.getQuestionId());
        }

        // Отправляем событие в Kafka
        eventProducer.sendAnswerSavedEvent(sessionId, session.getUserId(), session.getTestId(),
                request.getQuestionId(), request.getAnswer(), isCorrect);

        return convertToDto(answer);
    }

    // Временная заглушка для проверки ответов
    private boolean checkAnswer(Long questionId, String answer) {
        // TODO: Реальная проверка через test-management-service
        // Пока считаем все ответы правильными
        return true;
    }

    public List<AnswerDto> getSessionAnswers(Long sessionId) {
        return answerRepository.findBySessionId(sessionId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SessionResultDto calculateResults(Long sessionId) {
        List<Answer> answers = answerRepository.findBySessionId(sessionId);

        int totalQuestions = answers.size();
        int correctAnswers = (int) answers.stream().filter(a -> Boolean.TRUE.equals(a.getIsCorrect())).count();
        int totalPoints = answers.stream().mapToInt(Answer::getPointsEarned).sum();

        TestSession session = sessionRepository.findById(sessionId).orElse(null);

        return new SessionResultDto(
                sessionId,
                session != null ? session.getTestId() : null,
                session != null ? session.getUserId() : null,
                totalQuestions,
                correctAnswers,
                totalPoints,
                session != null ? session.getStatus().name() : "UNKNOWN",
                session != null ? session.getCompletedAt() : null
        );
    }

    private AnswerDto convertToDto(Answer answer) {
        return new AnswerDto(
                answer.getId(),
                answer.getQuestionId(),
                answer.getAnswer(),
                answer.getIsCorrect(),
                answer.getPointsEarned(),
                answer.getAnsweredAt()
        );
    }
}
