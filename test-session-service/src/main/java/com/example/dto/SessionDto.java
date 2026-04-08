package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {
    private Long id;
    private Long testId;
    private Long userId;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Integer timeLimitMinutes;
    private String status;
    private Integer totalAnswers;
    private Integer correctAnswers;
    private Integer totalPoints;
    private List<AnswerDto> answers;
}
