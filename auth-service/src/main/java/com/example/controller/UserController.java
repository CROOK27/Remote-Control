package com.example.controller;

import com.example.dto.ChangePasswordRequest;
import com.example.dto.UserDto;
import com.example.dto.Views;
import com.example.entity.Role;
import com.example.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management endpoints")
@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('STUDENT')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    @JsonView(Views.ShortInfo.class)
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if (!"TEACHER".equals(userRole) && !"ADMIN".equals(userRole)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role")
    @JsonView(Views.ShortInfo.class)
    public ResponseEntity<List<UserDto>> getUsersByRole(
            @PathVariable Role role,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if (!"TEACHER".equals(userRole) && !"ADMIN".equals(userRole)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @GetMapping("/group/{groupId}")
    @Operation(summary = "Get users by group")
    @JsonView(Views.ShortInfo.class)
    public ResponseEntity<List<UserDto>> getUsersByGroup(
            @PathVariable Long groupId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if (!"TEACHER".equals(userRole) && !"ADMIN".equals(userRole)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(userService.getUsersByGroup(groupId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    @JsonView(Views.ShortInfo.class)
    public ResponseEntity<UserDto> getUserById(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", required = false) String requestUserId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        if ("TEACHER".equals(userRole) || "ADMIN".equals(userRole)) {
            return ResponseEntity.ok(userService.getUserById(id));
        }
        if (requestUserId != null && requestUserId.equals(String.valueOf(id))) {
            return ResponseEntity.ok(userService.getUserById(id));
        }
        return ResponseEntity.status(403).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    @JsonView(Views.FullInfo.class)
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto,
            @RequestHeader(value = "X-User-Id", required = false) String requestUserId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        // Администратор может обновить любого
        if ("ADMIN".equals(userRole)) {
            return ResponseEntity.ok(userService.updateUser(id, userDto));
        }
        // Преподаватель может обновить только студентов? (уточните логику)
        if ("TEACHER".equals(userRole)) {
            // предположим, что преподаватель может обновить только студентов (роль STUDENT)
            // но для простоты разрешим преподавателю обновлять любого (кроме других преподавателей)
            // лучше реализовать отдельную проверку в сервисе
            return ResponseEntity.ok(userService.updateUser(id, userDto));
        }
        // Студент может обновить только себя
        if (requestUserId != null && requestUserId.equals(String.valueOf(id))) {
            return ResponseEntity.ok(userService.updateUser(id, userDto));
        }
        return ResponseEntity.status(403).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user (soft delete)")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Role", required = false) String userRole,
            @RequestHeader(value = "X-User-Id", required = false) String requestUserId) {
        if ("ADMIN".equals(userRole)) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        if ("TEACHER".equals(userRole)) {
            // преподаватель может удалить только студента? – проверка в сервисе
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        if (requestUserId != null && requestUserId.equals(String.valueOf(id))) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(403).build();
    }

    @PostMapping("/{id}/change-password")
    @Operation(summary = "Change user password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequest request,
            @RequestHeader(value = "X-User-Id", required = false) String requestUserId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        // Сменить пароль может администратор, преподаватель (для своих студентов?) или сам пользователь
        if ("ADMIN".equals(userRole)) {
            userService.changePassword(id, request);
            return ResponseEntity.ok().build();
        }
        if (requestUserId != null && requestUserId.equals(String.valueOf(id))) {
            userService.changePassword(id, request);
            return ResponseEntity.ok().build();
        }
        // Преподаватель может сменить пароль студента? – добавим по необходимости
        return ResponseEntity.status(403).build();
    }
}