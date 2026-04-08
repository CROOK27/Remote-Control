package com.example.controller;


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
    @Operation(summary = "Get total points for a user")
    public ResponseEntity<UserPointsDto> getUserPoints(@PathVariable Long userId) {
        return ResponseEntity.ok(gamificationService.getUserPoints(userId));
    }

    @GetMapping("/users/{userId}/history")
    @Operation(summary = "Get points history for a user")
    public ResponseEntity<List<PointsDto>> getUserHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(gamificationService.getUserHistory(userId));
    }

    @GetMapping("/leaderboard")
    @Operation(summary = "Get top users by points")
    public ResponseEntity<List<UserPointsDto>> getLeaderboard(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(gamificationService.getTopUsers(limit));
    }
}