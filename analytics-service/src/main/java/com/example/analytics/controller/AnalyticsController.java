package com.example.analytics.controller;

import com.example.analytics.client.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@Tag(name = "Аналитика", description = "Статистические отчёты")
@RequiredArgsConstructor
public class AnalyticsController {

    private final TestManagementClient testManagementClient;
    private final TestSessionClient testSessionClient;
    private final AuthClient authClient;
    private final ProctoringClient proctoringClient;
    private final GamificationClient gamificationClient;

    @GetMapping("/tests/{testId}")
    @Operation(summary = "Общая статистика по тесту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статистика"),
            @ApiResponse(responseCode = "404", description = "Тест не найден")
    })
    public ResponseEntity<?> getTestStatistics( @PathVariable Long testId) {
        Map<String, Object> report = new HashMap<>();
        try {
            report.put("test", testManagementClient.getTest(testId));
            report.put("questions", testManagementClient.getTestQuestions(testId));
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.status(503).body("Service unavailable: " + e.getMessage());
        }
    }

    @GetMapping("/students/{userId}")
    @Operation(summary = "Отчёт по конкретному студенту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Отчёт"),
            @ApiResponse(responseCode = "404", description = "Студент не найден")
    })
    public ResponseEntity<?> getStudentReport(@PathVariable Long userId) {
        Map<String, Object> report = new HashMap<>();
        try {
            report.put("user", authClient.getUser(userId));
            report.put("gamification", gamificationClient.getUserGamification(userId));
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.status(503).body("Service unavailable: " + e.getMessage());
        }
    }

    @GetMapping("/proctoring/{sessionId}")
    @Operation(summary = "Данные прокторинга по сессии")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные"),
            @ApiResponse(responseCode = "404", description = "Сессия не найдена")
    })
    public ResponseEntity<?> getProctoringReport(@PathVariable Long sessionId) {
        try {
            return ResponseEntity.ok(proctoringClient.getProctoringData(sessionId));
        } catch (Exception e) {
            return ResponseEntity.status(503).body("Service unavailable: " + e.getMessage());
        }
    }

    @GetMapping("/export")
    @Operation(summary = "Экспорт отчёта в файл")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл отчёта")
    })
    public ResponseEntity<String> exportReport(
            @Parameter(description = "Тип отчёта (test/group/student)") @RequestParam String type,
            @Parameter(description = "Формат (pdf/excel/csv)") @RequestParam String format) {
        return ResponseEntity.ok("Report file");
    }
}