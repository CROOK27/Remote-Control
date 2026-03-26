package com.example.gamification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/gamification")
@Tag(name = "Геймификация", description = "Игровые показатели и рейтинги")
public class GamificationEventController {

    @GetMapping("/sessions/{sessionId}/status")
    @Operation(summary = "Получить текущий игровой статус сессии")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус получен"),
            @ApiResponse(responseCode = "404", description = "Сессия не найдена")
    })
    public ResponseEntity<?> getGamificationStatus(
            @Parameter(description = "ID сессии") @PathVariable Long sessionId) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @GetMapping("/leaderboard/{testId}")
    @Operation(summary = "Таблица лидеров для теста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список лидеров"),
            @ApiResponse(responseCode = "404", description = "Тест не найден")
    })
    public ResponseEntity<?> getLeaderboard(
            @Parameter(description = "ID теста") @PathVariable Long testId) {
        return ResponseEntity.ok("OK"); // Заглушка
    }
}