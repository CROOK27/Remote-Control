package com.example.listener;


import com.example.event.AchievementUnlockedEvent;
import com.example.event.AnswerSavedEvent;
import com.example.event.SessionCompletedEvent;
import com.example.event.SuspiciousActionEvent;
import com.example.dto.SendNotificationRequest;
import com.example.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "session-events", groupId = "notification-group")
    public void handleSessionEvent(SessionCompletedEvent event) {
        log.info("Session completed: {}", event.getSessionId());

        SendNotificationRequest request = new SendNotificationRequest();
        request.setUserId(event.getUserId());
        request.setTitle("Session Completed");
        request.setMessage(String.format("You have completed session %d with score %d",
                event.getSessionId(), event.getScore()));
        request.setType("SESSION");
        request.setLink("/sessions/" + event.getSessionId());

        notificationService.createNotification(request);
    }

    @KafkaListener(topics = "answer-events", groupId = "notification-group")
    public void handleAnswerEvent(AnswerSavedEvent event) {
        if (event.getIsCorrect()) {
            SendNotificationRequest request = new SendNotificationRequest();
            request.setUserId(event.getUserId());
            request.setTitle("Correct Answer!");
            request.setMessage(String.format("Your answer to question %d was correct! +%d points",
                    event.getQuestionId(), 10));
            request.setType("ACHIEVEMENT");
            request.setLink("/sessions/" + event.getSessionId());

            notificationService.createNotification(request);
        }
    }

    @KafkaListener(topics = "gamification-events", groupId = "notification-group")
    public void handleAchievementEvent(AchievementUnlockedEvent event) {
        log.info("Achievement unlocked: {} for user {}", event.getAchievementName(), event.getUserId());

        SendNotificationRequest request = new SendNotificationRequest();
        request.setUserId(event.getUserId());
        request.setTitle("Achievement Unlocked! 🎉");
        request.setMessage(String.format("You have earned the achievement '%s' and received %d points!",
                event.getAchievementName(), event.getPoints()));
        request.setType("ACHIEVEMENT");

        notificationService.createNotification(request);
    }

    @KafkaListener(topics = "proctoring-events", groupId = "notification-group")
    public void handleProctoringEvent(SuspiciousActionEvent event) {
        log.info("Suspicious action detected: {} for session {}", event.getActionType(), event.getSessionId());

        SendNotificationRequest request = new SendNotificationRequest();
        request.setUserId(event.getUserId());
        request.setTitle("Proctoring Alert");
        request.setMessage(String.format("Suspicious action detected: %s. Please contact your instructor.",
                event.getActionType()));
        request.setType("ALERT");
        request.setLink("/sessions/" + event.getSessionId() + "/proctoring");

        notificationService.createNotification(request);
    }
}
