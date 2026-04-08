package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnswerRequest {
    @NotNull(message = "QuestionId is required")
    private Long questionId;

    private String answer;
}
