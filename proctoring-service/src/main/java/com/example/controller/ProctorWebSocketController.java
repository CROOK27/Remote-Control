package com.example.controller;


import com.example.dto.ProctoringEventDto;
import com.example.service.ProctoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProctorWebSocketController {

    private final ProctoringService proctorService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/proctor/event/{sessionId}")
    public void handleProctorEvent(@DestinationVariable Long sessionId, ProctoringEventDto event, Authentication auth) {
        boolean violation = proctorService.handleEvent(sessionId, event);
        if (violation) {
            messagingTemplate.convertAndSend("/topic/test/" + sessionId + "/violation",
                    "Student " + auth.getName() + " violated proctor rules");
            messagingTemplate.convertAndSendToUser(auth.getName(), "/queue/proctor/warning",
                    "Warning! Your session may be terminated if violations continue.");
        }
    }
}
