package org.example.group;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@Tag(name = "Группы", description = "Управление учебными группами")
public class GroupController {

    @GetMapping
    @Operation(summary = "Получить все группы", description = "Возможно фильтрация по специальности")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список групп")
    })
    public ResponseEntity<?> getAllGroups(
            @Parameter(description = "Идентификатор специальности")
            @RequestParam(required = false) Long specialtyId) {
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить группу по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Группа найдена"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    public ResponseEntity<?> getGroup(@PathVariable Long id) {
        return ResponseEntity.ok("Ok");
    }

    @PostMapping
    @Operation(summary = "Создать новую группу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Группа создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<?> createGroup() {
        return ResponseEntity.ok("Ok");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить группу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Группа обновлена"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    public ResponseEntity<?> updateGroup(@PathVariable Long id) {
        return ResponseEntity.ok("Ok");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить группу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Группа удалена"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "Получить список студентов в группе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список студентов"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    public ResponseEntity<?> getStudentsInGroup(@PathVariable Long id) {
        return ResponseEntity.ok("Ok");
    }
}