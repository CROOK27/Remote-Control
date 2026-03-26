package com.example.testSession;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
@Tag(name = "Сессии тестирования", description = "Прохождение тестов студентами")
public class TestSessionController {

    @PostMapping("/start")
    @Operation(summary = "Начать новую сессию тестирования")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сессия создана"),
            @ApiResponse(responseCode = "400", description = "Невозможно начать (нет попыток, истекло время и т.д.)")
    })
    public ResponseEntity<?> startSession() {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @GetMapping("/{sessionId}")
    @Operation(summary = "Получить информацию о сессии")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные сессии"),
            @ApiResponse(responseCode = "404", description = "Сессия не найдена")
    })
    public ResponseEntity<?> getSession(@PathVariable Long sessionId) {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @GetMapping("/{sessionId}/next")
    @Operation(summary = "Получить следующий вопрос (или первый, если сессия только началась)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вопрос для студента"),
            @ApiResponse(responseCode = "404", description = "Сессия не найдена"),
            @ApiResponse(responseCode = "204", description = "Вопросов больше нет (тест завершен)")
    })
    public ResponseEntity<?> getNextQuestion(@PathVariable Long sessionId) {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @PostMapping("/{sessionId}/answer")
    @Operation(summary = "Отправить ответ на вопрос")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат ответа (правильность, баллы)"),
            @ApiResponse(responseCode = "404", description = "Сессия или вопрос не найдены"),
            @ApiResponse(responseCode = "400", description = "Некорректный ответ")
    })
    public ResponseEntity<?> submitAnswer(
            @PathVariable Long sessionId) {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @PutMapping("/{sessionId}/finish")
    @Operation(summary = "Завершить сессию досрочно (или принудительно)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Итоговые результаты"),
            @ApiResponse(responseCode = "404", description = "Сессия не найдена")
    })
    public ResponseEntity<?> finishSession(@PathVariable Long sessionId) {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @GetMapping("/{sessionId}/results")
    @Operation(summary = "Получить результаты завершенной сессии")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результаты"),
            @ApiResponse(responseCode = "404", description = "Сессия не найдена или не завершена")
    })
    public ResponseEntity<?> getResults(@PathVariable Long sessionId) {
        return ResponseEntity.ok("ok"); // Заглушка
    }
}