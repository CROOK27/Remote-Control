package com.example.controller;

import com.example.dto.QuestionDto;
import com.example.dto.OptionDto;
import com.example.dto.MatchingPairDto;
import com.example.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@Tag(name = "Вопросы", description = "Управление вопросами и вариантами ответов")
@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/{testId}/questions")
    @Operation(summary = "Получить все вопросы теста")
    public ResponseEntity<List<QuestionDto>> getQuestions(
            @Parameter(description = "ID теста") @PathVariable Long testId) {
        return ResponseEntity.ok(questionService.getQuestionsByTestId(testId));
    }

    @PostMapping("/{testId}/questions")
    @Operation(summary = "Добавить новый вопрос к тесту")
    public ResponseEntity<QuestionDto> createQuestion(
            @PathVariable Long testId,
            @Valid @RequestBody QuestionDto questionDto) {
        return ResponseEntity.ok(questionService.createQuestion(testId, questionDto));
    }

    @GetMapping("/questions/{id}")
    @Operation(summary = "Получить вопрос по ID")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @PutMapping("/questions/{id}")
    @Operation(summary = "Обновить вопрос")
    public ResponseEntity<QuestionDto> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody QuestionDto questionDto) {
        return ResponseEntity.ok(questionService.updateQuestion(id, questionDto));
    }

    @DeleteMapping("/questions/{id}")
    @Operation(summary = "Удалить вопрос")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/questions/{id}/options")
    @Operation(summary = "Добавить вариант ответа к вопросу")
    public ResponseEntity<OptionDto> addOption(
            @PathVariable Long id,
            @Valid @RequestBody OptionDto optionDto) {
        return ResponseEntity.ok(questionService.addOption(id, optionDto));
    }

    @PutMapping("/options/{id}")
    @Operation(summary = "Обновить вариант ответа")
    public ResponseEntity<OptionDto> updateOption(
            @PathVariable Long id,
            @Valid @RequestBody OptionDto optionDto) {
        return ResponseEntity.ok(questionService.updateOption(id, optionDto));
    }

    @DeleteMapping("/options/{id}")
    @Operation(summary = "Удалить вариант ответа")
    public ResponseEntity<Void> deleteOption(@PathVariable Long id) {
        questionService.deleteOption(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/questions/{id}/pairs")
    @Operation(summary = "Добавить пару для сопоставления")
    public ResponseEntity<MatchingPairDto> addPair(
            @PathVariable Long id,
            @Valid @RequestBody MatchingPairDto pairDto) {
        return ResponseEntity.ok(questionService.addMatchingPair(id, pairDto));
    }

    @PutMapping("/pairs/{id}")
    @Operation(summary = "Обновить пару сопоставления")
    public ResponseEntity<MatchingPairDto> updatePair(
            @PathVariable Long id,
            @Valid @RequestBody MatchingPairDto pairDto) {
        return ResponseEntity.ok(questionService.updateMatchingPair(id, pairDto));
    }

    @DeleteMapping("/pairs/{id}")
    @Operation(summary = "Удалить пару сопоставления")
    public ResponseEntity<Void> deletePair(@PathVariable Long id) {
        questionService.deleteMatchingPair(id);
        return ResponseEntity.noContent().build();
    }
}