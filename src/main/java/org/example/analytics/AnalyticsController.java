package org.example.analytics;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@Tag(name = "Аналитика", description = "Статистические отчёты")
public class AnalyticsController {

    @GetMapping("/tests/{testId}")
    @Operation(summary = "Общая статистика по тесту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статистика"),
            @ApiResponse(responseCode = "404", description = "Тест не найден")
    })
    public ResponseEntity<?> getTestStatistics(@PathVariable Long testId) {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @GetMapping("/tests/{testId}/questions")
    @Operation(summary = "Детальный анализ по вопросам теста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статистика по вопросам"),
            @ApiResponse(responseCode = "404", description = "Тест не найден")
    })
    public ResponseEntity<?> getQuestionStatistics(@PathVariable Long testId) {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @GetMapping("/groups/{groupId}")
    @Operation(summary = "Статистика по группе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статистика группы"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    public ResponseEntity<?> getGroupStatistics(@PathVariable Long groupId) {
        return ResponseEntity.ok("OK"); // Заглушка
    }

    @GetMapping("/students/{userId}")
    @Operation(summary = "Отчёт по конкретному студенту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Отчёт"),
            @ApiResponse(responseCode = "404", description = "Студент не найден")
    })
    public ResponseEntity<?> getStudentReport(@PathVariable Long userId) {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @GetMapping("/proctoring/{sessionId}")
    @Operation(summary = "Данные прокторинга по сессии")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные"),
            @ApiResponse(responseCode = "404", description = "Сессия не найдена")
    })
    public ResponseEntity<?> getProctoringReport(@PathVariable Long sessionId) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @GetMapping("/export")
    @Operation(summary = "Экспорт отчёта в файл")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл отчёта")
    })
    public ResponseEntity<String> exportReport(
            @Parameter(description = "Тип отчёта (test/group/student)") @RequestParam String type,
            @Parameter(description = "Формат (pdf/excel/csv)") @RequestParam String format) {
        // Здесь должен генерироваться файл и возвращаться как Resource
        return ResponseEntity.ok("Report file");
    }
}