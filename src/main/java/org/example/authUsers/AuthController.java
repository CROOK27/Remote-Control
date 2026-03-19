package org.example.authUsers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Аутентификация", description = "Регистрация, вход, выход и обновление токена")
public class AuthController {

    @PostMapping("/register")
    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<String> register() {
        // В реальном методе принимается DTO с данными пользователя
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    @Operation(summary = "Вход в систему", description = "Возвращает JWT токен")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная аутентификация"),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    })
    public ResponseEntity<String> login() {
        // Принимает логин/пароль, возвращает JWT
        return ResponseEntity.ok("JWT token");
    }

    @PostMapping("/logout")
    @Operation(summary = "Выход из системы", description = "Инвалидация токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Выход выполнен")
    })
    public ResponseEntity<String> logout(
            @Parameter(description = "JWT токен") @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok("Logged out");
    }

    @PostMapping("/refresh")
    @Operation(summary = "Обновление JWT токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый токен выдан"),
            @ApiResponse(responseCode = "401", description = "Недействительный refresh токен")
    })
    public ResponseEntity<String> refresh(
            @Parameter(description = "Refresh токен") @RequestHeader("Authorization") String refreshToken) {
        return ResponseEntity.ok("New JWT token");
    }
}