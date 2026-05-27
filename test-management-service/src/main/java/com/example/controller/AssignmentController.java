package com.example.controller;


import com.example.dto.AssignmentDto;
import com.example.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@Tag(name = "Назначения тестов", description = "Связь тестов с группами")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping
    @Operation(summary = "Список назначений")
    public ResponseEntity<List<AssignmentDto>> getAssignments(
            @Parameter(description = "ID теста") @RequestParam(required = false) Long testId,
            @Parameter(description = "ID группы") @RequestParam(required = false) Long groupId) {

        if (testId != null) {
            return ResponseEntity.ok(assignmentService.getAssignmentsByTestId(testId));
        } else if (groupId != null) {
            return ResponseEntity.ok(assignmentService.getAssignmentsByGroupId(groupId));
        } else {
            return ResponseEntity.ok(assignmentService.getAllAssignments());
        }
    }

    @PostMapping
    @Operation(summary = "Назначить тест группе")
    public ResponseEntity<AssignmentDto> createAssignment(@Valid @RequestBody AssignmentDto dto) {
        return ResponseEntity.ok(assignmentService.createAssignment(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить назначение")
    public ResponseEntity<AssignmentDto> updateAssignment(
            @PathVariable Long id,
            @Valid @RequestBody AssignmentDto dto) {
        return ResponseEntity.ok(assignmentService.updateAssignment(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить назначение")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/groups/{groupId}")
    @Operation(summary = "Назначения для конкретной группы")
    public ResponseEntity<List<AssignmentDto>> getAssignmentsByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByGroupId(groupId));
    }

    @GetMapping("/tests/{testId}")
    @Operation(summary = "Группы, которым назначен тест")
    public ResponseEntity<List<AssignmentDto>> getAssignmentsByTest(@PathVariable Long testId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByTestId(testId));
    }
}