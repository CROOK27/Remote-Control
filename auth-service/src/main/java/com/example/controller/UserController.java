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
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role")
    @JsonView(Views.ShortInfo.class)
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable Role role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @GetMapping("/group/{groupId}")
    @Operation(summary = "Get users by group")
    @JsonView(Views.ShortInfo.class)
    public ResponseEntity<List<UserDto>> getUsersByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(userService.getUsersByGroup(groupId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    @JsonView(Views.ShortInfo.class)
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    @JsonView(Views.FullInfo.class)
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user (soft delete)")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    @Operation(summary = "Change user password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request);
        return ResponseEntity.ok().build();
    }
}