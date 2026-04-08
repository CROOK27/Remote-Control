package com.example.listener;


import com.example.event.AnswerSavedEvent;
import com.example.event.SessionCompletedEvent;
import com.example.service.GamificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GamificationEventListener {

    private final GamificationService gamificationService;

    @KafkaListener(topics = "answer-events", groupId = "${spring.application.name}-group")
    public void handleAnswerSaved(AnswerSavedEvent event) {
        log.info("Received AnswerSavedEvent: sessionId={}, questionId={}, isCorrect={}",
                event.getSessionId(), event.getQuestionId(), event.getIsCorrect());

        gamificationService.processCorrectAnswer(event);
    }

    @KafkaListener(topics = "session-events", groupId = "${spring.application.name}-group")
    public void handleSessionCompleted(SessionCompletedEvent event) {
        log.info("Received SessionCompletedEvent: sessionId={}, score={}",
                event.getSessionId(), event.getScore());

        gamificationService.processSessionCompleted(event);
    }
}