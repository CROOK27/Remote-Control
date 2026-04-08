package com.example.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionCompletedEvent {
    private Long sessionId;
    private Long userId;
    private Long testId;
    private Integer score;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private LocalDateTime completedAt;
}