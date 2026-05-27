package com.example.service;


import com.example.event.SuspiciousActionEvent;
import com.example.dto.ProctoringEventDto;
import com.example.dto.ProctoringRequest;
import com.example.dto.SuspiciousActionDto;
import com.example.entity.ProctoringEvent;
import com.example.entity.SuspiciousAction;
import com.example.producer.ProctoringEventProducer;
import com.example.repository.ProctoringEventRepository;
import com.example.repository.SuspiciousActionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProctoringService {

    private final ProctoringEventRepository proctoringEventRepository;
    private final SuspiciousActionRepository suspiciousActionRepository;
    private final ProctoringEventProducer eventProducer;
    private final RestTemplate restTemplate;

    @Value("${session.service.url:http://localhost:8081}")

    private String sessionServiceUrl;
    private static final int DEFAULT_VIOLATION_LIMIT = 3;

    @Transactional
    public ProctoringEventDto logEvent(ProctoringRequest request) {
        // Определяем, является ли событие подозрительным
        boolean isSuspicious = isSuspiciousEvent(request.getEventType());

        // Сохраняем событие
        ProctoringEvent event = new ProctoringEvent();
        event.setSessionId(request.getSessionId());
        event.setUserId(request.getUserId());
        event.setEventType(request.getEventType());
        event.setDetails(request.getDetails());
        event.setIsSuspicious(isSuspicious);
        event = proctoringEventRepository.save(event);

        // Если событие подозрительное, создаём запись и отправляем в Kafka
        if (isSuspicious) {
            createSuspiciousAction(request, event.getId());
        }

        return convertToDto(event);
    }

    private boolean isSuspiciousEvent(String eventType) {
        // События, которые считаются подозрительными
        return "TAB_SWITCH".equals(eventType) ||
                "WINDOW_BLUR".equals(eventType) ||
                "PAGE_LEAVE".equals(eventType);
    }

    private void createSuspiciousAction(ProctoringRequest request, Long eventId) {
        SuspiciousAction action = new SuspiciousAction();
        action.setSessionId(request.getSessionId());
        action.setUserId(request.getUserId());
        action.setActionType(request.getEventType());
        action.setDescription(buildDescription(request));
        action = suspiciousActionRepository.save(action);

        // Отправляем событие в Kafka
        SuspiciousActionEvent kafkaEvent = new SuspiciousActionEvent(
                request.getSessionId(),
                request.getUserId(),
                request.getEventType(),
                buildDescription(request),
                LocalDateTime.now()
        );
        eventProducer.sendSuspiciousActionEvent(kafkaEvent);

        log.info("Suspicious action detected: {} for session {}", request.getEventType(), request.getSessionId());
    }

    private String buildDescription(ProctoringRequest request) {
        if (request.getDetails() != null && !request.getDetails().isEmpty()) {
            return request.getDetails();
        }

        switch (request.getEventType()) {
            case "TAB_SWITCH":
                return "Student switched to another tab";
            case "WINDOW_BLUR":
                return "Window lost focus";
            case "PAGE_LEAVE":
                return "Student left the page";
            default:
                return "Suspicious activity detected";
        }
    }

    public List<ProctoringEventDto> getSessionEvents(Long sessionId) {
        return proctoringEventRepository.findBySessionId(sessionId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProctoringEventDto> getUserEvents(Long userId) {
        return proctoringEventRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SuspiciousActionDto> getSuspiciousActions(Long sessionId) {
        return suspiciousActionRepository.findBySessionId(sessionId).stream()
                .map(this::convertToSuspiciousDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void resolveSuspiciousAction(Long actionId) {
        SuspiciousAction action = suspiciousActionRepository.findById(actionId)
                .orElseThrow(() -> new RuntimeException("Action not found"));
        action.setResolved(true);
        suspiciousActionRepository.save(action);
        log.info("Suspicious action {} resolved", actionId);
    }

    private ProctoringEventDto convertToDto(ProctoringEvent event) {
        return new ProctoringEventDto(
                event.getId(),
                event.getSessionId(),
                event.getUserId(),
                event.getEventType(),
                event.getDetails(),
                event.getEventTime(),
                event.getIsSuspicious()
        );
    }

    private SuspiciousActionDto convertToSuspiciousDto(SuspiciousAction action) {
        return new SuspiciousActionDto(
                action.getId(),
                action.getSessionId(),
                action.getUserId(),
                action.getActionType(),
                action.getDescription(),
                action.getSeverity(),
                action.getDetectedAt(),
                action.getResolved()
        );
    }

    public boolean handleEvent(Long sessionId, ProctoringEventDto event) {
        ProctoringEvent proctorEvent = new ProctoringEvent();
        proctorEvent.setSessionId(sessionId);
        proctorEvent.setEventType(event.getEventType());
        proctorEvent.setEventTime(LocalDateTime.now());
        proctoringEventRepository.save(proctorEvent);
        int violationCount = proctoringEventRepository.countBySessionIdAndEventType(sessionId, event.getEventType());
        int limit = getViolationLimit(sessionId);
        if (violationCount > limit) {
            String url = sessionServiceUrl + "/api/sessions/" + sessionId + "/terminate";
            try {
                restTemplate.postForObject(url, null, Void.class);
            } catch (Exception e) {
                System.err.println("Failed to call session service: " + e.getMessage());
            }

            SuspiciousAction action = new SuspiciousAction();
            action.setSessionId(sessionId);
            action.setActionType(event.getEventType());
            action.setDescription("Exceeded violation limit: " + violationCount + " > " + limit);
            action.setSeverity("HIGH");
            action.setDetectedAt(LocalDateTime.now());
            action.setResolved(false);
            //action.setUserId(sessionId.); //TODO: Получение айди пользователя из сессий
            suspiciousActionRepository.save(action);

            return true;
        }
        return false;
    }

    private int getViolationLimit(Long sessionId) {
        // Можно загружать из БД (настроек теста, связанного с сессией)
        // Пока вернём константу
        return DEFAULT_VIOLATION_LIMIT;
    }
}
