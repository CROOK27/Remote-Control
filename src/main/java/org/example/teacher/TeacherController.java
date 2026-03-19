package org.example.teacher;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Преподаватели", description = "Дополнительная информация о преподавателях")
public class TeacherController {

    @GetMapping
    @Operation(summary = "Список всех преподавателей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список получен")
    })
    public ResponseEntity<?> getAllTeachers() {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Получить данные преподавателя по ID пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные найдены"),
            @ApiResponse(responseCode = "404", description = "Преподаватель не найден")
    })
    public ResponseEntity<?> getTeacherByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @PostMapping
    @Operation(summary = "Создать запись преподавателя (для существующего пользователя)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запись создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<?> createTeacher() {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Обновить данные преподавателя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные обновлены"),
            @ApiResponse(responseCode = "404", description = "Преподаватель не найден")
    })
    public ResponseEntity<?> updateTeacher(
            @PathVariable Long userId) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Удалить запись преподавателя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Запись удалена"),
            @ApiResponse(responseCode = "404", description = "Преподаватель не найден")
    })
    public ResponseEntity<?> deleteTeacher(@PathVariable Long userId) {
        return ResponseEntity.noContent().build();
    }
}