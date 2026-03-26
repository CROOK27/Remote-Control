package com.example.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerSavedEvent {
    private Long sessionId;
    private Long questionId;
    private String answer;
    private Boolean isCorrect;
    private LocalDateTime answeredAt;
}