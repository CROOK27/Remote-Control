package com.example.controller;


import com.example.dto.LeaderboardEntry;
import com.example.dto.PointsDto;
import com.example.dto.UserPointsDto;
import com.example.service.GamificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gamification")
@Tag(name = "Gamification", description = "User points management")
@RequiredArgsConstructor
public class GamificationController {

    private final GamificationService gamificationService;

    @GetMapping("/users/{userId}/points")
    public ResponseEntity<UserPointsDto> getUserPoints(
            @PathVariable Long userId,
            @RequestHeader(value = "X-User-Id", required = false) String requestUserId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {

        if ("TEACHER".equals(userRole) || "ADMIN".equals(userRole)) {
            return ResponseEntity.ok(gamificationService.getUserPoints(userId));
        }
        if (requestUserId != null && requestUserId.equals(String.valueOf(userId))) {
            return ResponseEntity.ok(gamificationService.getUserPoints(userId));
        }
        return ResponseEntity.status(403).build();
    }

    @GetMapping("/users/{userId}/history")
    @Operation(summary = "Get points history for a user")
    public ResponseEntity<List<PointsDto>> getUserHistory(
            @PathVariable Long testId,
            @RequestHeader(value = "X-User-Role", required = false) @PathVariable Long userId) {
        return ResponseEntity.ok(gamificationService.getUserHistory(userId));
    }

    @GetMapping("/leaderboard")
    @Operation(summary = "Get top users by points")
    public ResponseEntity<List<UserPointsDto>> getLeaderboard(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(gamificationService.getTopUsers(limit));
    }
    @GetMapping("/test/{testId}/leaderboard")
    public ResponseEntity<List<LeaderboardEntry>> getTestLeaderboard(
            @PathVariable Long testId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        // Лидерборд может смотреть преподаватель или администратор
        if (!"TEACHER".equals(userRole) && !"ADMIN".equals(userRole)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(gamificationService.getTestLeaderboard(testId));
    }
}