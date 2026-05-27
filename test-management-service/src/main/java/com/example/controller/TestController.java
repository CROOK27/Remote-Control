package com.example.controller;

import com.example.dto.CreateTestRequest;
import com.example.dto.TestDto;
import com.example.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@Tag(name = "Tests", description = "Test management endpoints")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping
    @Operation(summary = "Get all tests")
    public ResponseEntity<List<TestDto>> getAllTests() {
        return ResponseEntity.ok(testService.getAllTests());
    }

    @GetMapping("/teacher/{teacherId}")
    @Operation(summary = "Get tests by teacher")
    public ResponseEntity<List<TestDto>> getTestsByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(testService.getTestsByTeacher(teacherId));
    }

    @GetMapping("/published")
    @Operation(summary = "Get published tests")
    public ResponseEntity<List<TestDto>> getPublishedTests() {
        return ResponseEntity.ok(testService.getPublishedTests());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get test by ID")
    public ResponseEntity<TestDto> getTestById(@PathVariable Long id) {
        return ResponseEntity.ok(testService.getTestById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new test")
    public ResponseEntity<TestDto> createTest(@Valid @RequestBody CreateTestRequest request) {
        return ResponseEntity.ok(testService.createTest(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a test")
    public ResponseEntity<TestDto> updateTest(@PathVariable Long id, @Valid @RequestBody CreateTestRequest request) {
        return ResponseEntity.ok(testService.updateTest(id, request));
    }

    @PostMapping("/{id}/publish")
    @Operation(summary = "Publish a test")
    public ResponseEntity<TestDto> publishTest(@PathVariable Long id) {
        return ResponseEntity.ok(testService.publishTest(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a test")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }
}