package com.example.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tests")
@Tag(name = "Тесты", description = "Управление тестами")
public class TestController {

    @GetMapping
    @Operation(summary = "Список тестов", description = "Фильтрация по статусу и автору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список получен")
    })
    public ResponseEntity<?> getTests(
            @Parameter(description = "Статус теста (DRAFT, PUBLISHED, ARCHIVED)")
            @RequestParam(required = false) String status,
            @Parameter(description = "ID автора")
            @RequestParam(required = false) Long authorId) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @GetMapping("/{id}")
    @Operation(summary = "Детальная информация о тесте")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тест найден"),
            @ApiResponse(responseCode = "404", description = "Тест не найден")
    })
    public ResponseEntity<?> getTest(@PathVariable Long id) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @PostMapping
    @Operation(summary = "Создать новый тест (черновик)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тест создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<?> createTest() {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить параметры теста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тест обновлен"),
            @ApiResponse(responseCode = "404", description = "Тест не найден")
    })
    public ResponseEntity<?> updateTest(
            @PathVariable Long id) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тест (или архивировать)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Тест удален"),
            @ApiResponse(responseCode = "404", description = "Тест не найден")
    })
    public ResponseEntity<?> deleteTest(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/publish")
    @Operation(summary = "Опубликовать тест")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тест опубликован"),
            @ApiResponse(responseCode = "404", description = "Тест не найден"),
            @ApiResponse(responseCode = "400", description = "Тест не может быть опубликован (нет вопросов)")
    })
    public ResponseEntity<String> publishTest(@PathVariable Long id) {
        return ResponseEntity.ok("Test published");
    }

    @PutMapping("/{id}/archive")
    @Operation(summary = "Архивировать тест")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тест архивирован"),
            @ApiResponse(responseCode = "404", description = "Тест не найден")
    })
    public ResponseEntity<String> archiveTest(@PathVariable Long id) {
        return ResponseEntity.ok("Test archived");
    }

    @GetMapping("/{id}/preview")
    @Operation(summary = "Предварительный просмотр теста (как для студента)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные для предпросмотра"),
            @ApiResponse(responseCode = "404", description = "Тест не найден")
    })
    public ResponseEntity<?> previewTest(@PathVariable Long id) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }
}