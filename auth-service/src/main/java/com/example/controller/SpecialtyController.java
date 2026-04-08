package com.example.controller;


import com.example.dto.SpecialtyDto;
import com.example.service.SpecialtyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialties")
@Tag(name = "Specialties", description = "Specialty management endpoints")
@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
@RequiredArgsConstructor
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @GetMapping
    @Operation(summary = "Get all specialties")
    public ResponseEntity<List<SpecialtyDto>> getAllSpecialties() {
        return ResponseEntity.ok(specialtyService.getAllSpecialties());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get specialty by ID")
    public ResponseEntity<SpecialtyDto> getSpecialtyById(@PathVariable Long id) {
        return ResponseEntity.ok(specialtyService.getSpecialtyById(id));
    }

    @PostMapping
    @Operation(summary = "Create new specialty")
    public ResponseEntity<SpecialtyDto> createSpecialty(@RequestBody SpecialtyDto specialtyDto) {
        return ResponseEntity.ok(specialtyService.createSpecialty(specialtyDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update specialty")
    public ResponseEntity<SpecialtyDto> updateSpecialty(@PathVariable Long id, @RequestBody SpecialtyDto specialtyDto) {
        return ResponseEntity.ok(specialtyService.updateSpecialty(id, specialtyDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete specialty")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Long id) {
        specialtyService.deleteSpecialty(id);
        return ResponseEntity.noContent().build();
    }
}