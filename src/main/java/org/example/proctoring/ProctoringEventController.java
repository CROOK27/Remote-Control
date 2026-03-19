package org.example.proctoring;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proctoring")
@Tag(name = "Прокторинг", description = "Приём событий контроля от клиента")
public class ProctoringEventController {

    @PostMapping("/events")
    @Operation(summary = "Отправить пакет событий прокторинга")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "События приняты"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<String> sendEvents(@RequestBody List<?> events) {
        // Здесь должна быть логика сохранения событий
        return ResponseEntity.ok("Events received");
    }
}