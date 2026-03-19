package org.example.dashboard;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Главная страница", description = "Сводные данные для разных ролей")
public class DashboardController {

    @GetMapping
    @Operation(summary = "Получить данные для дашборда", description = "Возвращает информацию в зависимости от роли пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "401", description = "Не авторизован")
    })
    public ResponseEntity<?> getDashboard() {
        // Здесь должна быть логика формирования сводки
        return ResponseEntity.ok("Ok");
    }
}