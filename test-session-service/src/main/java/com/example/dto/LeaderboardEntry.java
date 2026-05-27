package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LeaderboardEntry {
    private String studentName;
    private int score;

    public LeaderboardEntry(Long userId, int score) {
        this.studentName = String.valueOf(userId);
        this.score = score;
    }
}