package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StartSessionRequest {
    @NotNull(message = "TestId is required")
    private Long testId;

    @NotNull(message = "UserId is required")
    private Long userId;
}
