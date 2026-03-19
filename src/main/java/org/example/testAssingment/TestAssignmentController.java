package org.example.testAssingment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@Tag(name = "Назначения тестов", description = "Связь тестов с группами")
public class TestAssignmentController {

    @GetMapping
    @Operation(summary = "Список назначений", description = "Фильтрация по тесту и группе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список получен")
    })
    public ResponseEntity<?> getAssignments(
            @Parameter(description = "ID теста") @RequestParam(required = false) Long testId,
            @Parameter(description = "ID группы") @RequestParam(required = false) Long groupId) {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @PostMapping
    @Operation(summary = "Назначить тест группе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Назначение создано"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<?> createAssignment() {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить назначение (дедлайн, активность)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Назначение обновлено"),
            @ApiResponse(responseCode = "404", description = "Назначение не найдено")
    })
    public ResponseEntity<?> updateAssignment(
            @PathVariable Long id) {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить назначение")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Назначение удалено"),
            @ApiResponse(responseCode = "404", description = "Назначение не найдено")
    })
    public ResponseEntity<?> deleteAssignment(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/groups/{groupId}")
    @Operation(summary = "Назначения для конкретной группы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список назначений"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    public ResponseEntity<?> getAssignmentsByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @GetMapping("/tests/{testId}")
    @Operation(summary = "Группы, которым назначен тест")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список назначений"),
            @ApiResponse(responseCode = "404", description = "Тест не найден")
    })
    public ResponseEntity<?> getAssignmentsByTest(@PathVariable Long testId) {
        return ResponseEntity.ok("ok"); // Заглушка
    }
}