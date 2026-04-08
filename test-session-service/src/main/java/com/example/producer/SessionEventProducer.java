package com.example.producer;


import com.example.event.AnswerSavedEvent;
import com.example.event.SessionCompletedEvent;
import com.example.event.SessionStartedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendSessionStartedEvent(Long sessionId, Long userId, Long testId) {
        SessionStartedEvent event = new SessionStartedEvent(sessionId, userId, testId, LocalDateTime.now());
        kafkaTemplate.send("session-events", event);
        log.info("Sent SessionStartedEvent for session: {}", sessionId);
    }

    public void sendSessionCompletedEvent(Long sessionId, Long userId, Long testId,
                                          Integer score, Integer totalQuestions,
                                          Integer correctAnswers) {
        SessionCompletedEvent event = new SessionCompletedEvent(
                sessionId, userId, testId, score, totalQuestions, correctAnswers, LocalDateTime.now()
        );
        kafkaTemplate.send("session-events", event);
        log.info("Sent SessionCompletedEvent for session: {}", sessionId);
    }

    public void sendAnswerSavedEvent(Long sessionId, Long userId, Long questionId,
                                     String answer, Boolean isCorrect) {
        AnswerSavedEvent event = new AnswerSavedEvent(
                sessionId, userId, questionId, answer, isCorrect, LocalDateTime.now()
        );
        kafkaTemplate.send("answer-events", event);
        log.info("Sent AnswerSavedEvent for session: {}, question: {}", sessionId, questionId);
    }
}
