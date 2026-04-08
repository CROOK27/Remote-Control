package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProctoringRequest {
    @NotNull(message = "SessionId is required")
    private Long sessionId;

    @NotNull(message = "UserId is required")
    private Long userId;

    @NotBlank(message = "EventType is required")
    private String eventType;

    private String details;
}
