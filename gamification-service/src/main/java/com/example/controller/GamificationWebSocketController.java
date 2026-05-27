package com.example.controller;


import com.example.service.GamificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GamificationWebSocketController {

    private final GamificationService gamificationService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/gamification/leaderboard/{testId}")
    public void getLeaderboard(@DestinationVariable Long testId) {
        var leaderboard = gamificationService.getTestLeaderboard(testId);
        messagingTemplate.convertAndSend("/topic/gamification/" + testId + "/leaderboard", leaderboard);
    }

    @Scheduled(fixedDelay = 10000)
    public void pushLeaderboardUpdates() {
        var activeTests = gamificationService.getActiveTestIds();
        for (Long testId : activeTests) {
            var leaderboard = gamificationService.getTestLeaderboard(testId);
            messagingTemplate.convertAndSend("/topic/gamification/" + testId + "/leaderboard", leaderboard);
        }
    }
}
