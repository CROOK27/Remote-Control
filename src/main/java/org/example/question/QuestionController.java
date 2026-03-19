package org.example.question;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Вопросы", description = "Управление вопросами и вариантами ответов")
public class QuestionController {

    @GetMapping("/tests/{testId}/questions")
    @Operation(summary = "Получить все вопросы теста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список вопросов"),
            @ApiResponse(responseCode = "404", description = "Тест не найден")
    })
    public ResponseEntity<?> getQuestions(
            @Parameter(description = "ID теста") @PathVariable Long testId) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @PostMapping("/tests/{testId}/questions")
    @Operation(summary = "Добавить новый вопрос к тесту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вопрос создан"),
            @ApiResponse(responseCode = "404", description = "Тест не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<?> createQuestion(
            @PathVariable Long testId) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @GetMapping("/questions/{id}")
    @Operation(summary = "Получить вопрос по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вопрос найден"),
            @ApiResponse(responseCode = "404", description = "Вопрос не найден")
    })
    public ResponseEntity<?> getQuestion(@PathVariable Long id) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @PutMapping("/questions/{id}")
    @Operation(summary = "Обновить вопрос")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вопрос обновлен"),
            @ApiResponse(responseCode = "404", description = "Вопрос не найден")
    })
    public ResponseEntity<?> updateQuestion(
            @PathVariable Long id) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @DeleteMapping("/questions/{id}")
    @Operation(summary = "Удалить вопрос")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Вопрос удален"),
            @ApiResponse(responseCode = "404", description = "Вопрос не найден")
    })
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/questions/{id}/options")
    @Operation(summary = "Добавить вариант ответа к вопросу (для SINGLE/MULTIPLE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вариант добавлен"),
            @ApiResponse(responseCode = "404", description = "Вопрос не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный тип вопроса")
    })
    public ResponseEntity<?> addOption(
            @PathVariable Long id) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @PutMapping("/options/{id}")
    @Operation(summary = "Обновить вариант ответа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вариант обновлен"),
            @ApiResponse(responseCode = "404", description = "Вариант не найден")
    })
    public ResponseEntity<?> updateOption(
            @PathVariable Long id) {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @DeleteMapping("/options/{id}")
    @Operation(summary = "Удалить вариант ответа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Вариант удален"),
            @ApiResponse(responseCode = "404", description = "Вариант не найден")
    })
    public ResponseEntity<?> deleteOption(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/questions/{id}/pairs")
    @Operation(summary = "Добавить пару для сопоставления (для MATCHING)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пара добавлена"),
            @ApiResponse(responseCode = "404", description = "Вопрос не найден")
    })
    public ResponseEntity<?> addPair(
            @PathVariable Long id) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @PutMapping("/pairs/{id}")
    @Operation(summary = "Обновить пару сопоставления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пара обновлена"),
            @ApiResponse(responseCode = "404", description = "Пара не найдена")
    })
    public ResponseEntity<?> updatePair(
            @PathVariable Long id) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @DeleteMapping("/pairs/{id}")
    @Operation(summary = "Удалить пару сопоставления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пара удалена"),
            @ApiResponse(responseCode = "404", description = "Пара не найдена")
    })
    public ResponseEntity<?> deletePair(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}