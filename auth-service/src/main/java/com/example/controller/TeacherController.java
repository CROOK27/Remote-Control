package com.example.controller;

import com.example.dto.TeacherDto;
import com.example.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Teachers", description = "Teacher management endpoints")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    @Operation(summary = "Get all teachers")
    public ResponseEntity<List<TeacherDto>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get teacher by user ID")
    public ResponseEntity<TeacherDto> getTeacherByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(teacherService.getTeacherByUserId(userId));
    }

    @PostMapping
    @Operation(summary = "Create teacher profile")
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody TeacherDto teacherDto) {
        return ResponseEntity.ok(teacherService.createTeacher(teacherDto));
    }

    @PutMapping("/user/{userId}")
    @Operation(summary = "Update teacher profile")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable Long userId, @RequestBody TeacherDto teacherDto) {
        return ResponseEntity.ok(teacherService.updateTeacher(userId, teacherDto));
    }

    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Delete teacher profile")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long userId) {
        teacherService.deleteTeacher(userId);
        return ResponseEntity.noContent().build();
    }
}