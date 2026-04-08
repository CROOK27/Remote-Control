package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionResultDto {
    private Long sessionId;
    private Long testId;
    private Long userId;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private Integer totalPoints;
    private String status;
    private LocalDateTime completedAt;
}
