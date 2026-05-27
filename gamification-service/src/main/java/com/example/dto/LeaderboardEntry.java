package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardEntry {
    private Long userId;
    private int score;

    // Если нужен конструктор для (Long, Long) – добавим
    public LeaderboardEntry(Long userId, Long score) {
        this.userId = userId;
        this.score = score != null ? score.intValue() : 0;
    }
}