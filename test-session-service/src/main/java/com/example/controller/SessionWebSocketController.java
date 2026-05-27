package com.example.controller;

import com.example.dto.AnswerRequest;
import com.example.dto.QuestionDto;
import com.example.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SessionWebSocketController {

    private final SessionService sessionService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/session/{sessionId}/next")
    public void getNextQuestion(@DestinationVariable Long sessionId, Authentication auth) {
        QuestionDto next = sessionService.getNextQuestion(sessionId, auth.getName());
        messagingTemplate.convertAndSendToUser(auth.getName(), "/queue/session/next", next);
    }
}
