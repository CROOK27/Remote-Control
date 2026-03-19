package org.example.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Пользователи", description = "Управление учетными записями пользователей")
public class UserController {

    @GetMapping
    @Operation(summary = "Получить список пользователей", description = "Поддерживает фильтрацию по роли и группе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список получен")
    })
    public ResponseEntity<?> getUsers(
            @Parameter(description = "Фильтр по роли (STUDENT/TEACHER)")
            @RequestParam(required = false) String role,
            @Parameter(description = "Фильтр по группе")
            @RequestParam(required = false) Long groupId) {
        return ResponseEntity.ok("Ok"); // Заглушка
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные обновлены"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<?> updateUser(
            @PathVariable Long id
            ) {
        return ResponseEntity.ok("ok"); // Заглушка
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Деактивировать/удалить пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/change-password")
    @Operation(summary = "Сменить пароль")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пароль изменен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "400", description = "Неверный старый пароль")
    })
    public ResponseEntity<String> changePassword(
            @PathVariable Long id
            ) {
        return ResponseEntity.ok("Password changed");
    }
}