package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointsDto {
    private Long sessionId;
    private Long questionId;
    private Integer pointsEarned;
    private LocalDateTime earnedAt;
}