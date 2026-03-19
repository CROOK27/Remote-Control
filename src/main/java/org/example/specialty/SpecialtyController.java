package org.example.specialty;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/specialties")
@Tag(name = "Специальности", description = "Справочник специальностей")
public class SpecialtyController {

    @GetMapping
    @Operation(summary = "Получить все специальности")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список получен")
    })
    public ResponseEntity<?> getAllSpecialties() {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить специальность по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Специальность найдена"),
            @ApiResponse(responseCode = "404", description = "Специальность не найдена")
    })
    public ResponseEntity<?> getSpecialty(@PathVariable Long id) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @PostMapping
    @Operation(summary = "Создать новую специальность")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Специальность создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<?> createSpecialty() {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить специальность")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Специальность обновлена"),
            @ApiResponse(responseCode = "404", description = "Специальность не найдена")
    })
    public ResponseEntity<?> updateSpecialty(
            @PathVariable Long id) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить специальность")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Специальность удалена"),
            @ApiResponse(responseCode = "404", description = "Специальность не найдена")
    })
    public ResponseEntity<?> deleteSpecialty(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}