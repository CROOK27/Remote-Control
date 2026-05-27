package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionProgressUpdate {
    private int currentScore;
    private int answeredCount;
    private int totalQuestions;
    private Integer streak;
    private List<LeaderboardEntry> leaderboardUpdate;

    public void setLeaderboardUpdate(List<LeaderboardEntry> leaderboardUpdate) {

    }
}
