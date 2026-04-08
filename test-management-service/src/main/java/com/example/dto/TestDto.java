package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {
    private Long id;
    private String title;
    private String description;
    private Long teacherId;
    private Integer timeLimitMinutes;
    private Integer attemptsAllowed;
    private Boolean shuffleQuestions;
    private Boolean shuffleOptions;
    private String status;
    private List<QuestionDto> questions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
