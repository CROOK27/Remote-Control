package com.example.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationWebSocketSender {

    private final SimpMessagingTemplate messagingTemplate;

    @Async
    public void sendToStudent(String studentEmail, String message) {
        messagingTemplate.convertAndSendToUser(studentEmail, "/queue/notifications", message);
    }

    public void broadcastToTestSession(Long testId, String message) {
        messagingTemplate.convertAndSend("/topic/test/" + testId + "/broadcast", message);
    }
}
