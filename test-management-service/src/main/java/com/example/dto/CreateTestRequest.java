package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateTestRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "TeacherId is required")
    private Long teacherId;

    private Integer attemptsAllowed = 1;

    private Boolean shuffleQuestions = false;

    private Boolean shuffleOptions = false;

    private List<QuestionDto> questions;
}