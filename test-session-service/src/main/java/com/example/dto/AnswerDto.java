package com.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {
    private Long id;
    private Long questionId;
    private String answer;
    private Boolean isCorrect;
    private Integer pointsEarned;
    private LocalDateTime answeredAt;
}
