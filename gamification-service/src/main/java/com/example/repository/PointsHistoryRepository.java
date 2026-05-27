package com.example.repository;


import com.example.dto.LeaderboardEntry;
import com.example.entity.PointsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointsHistoryRepository extends JpaRepository<PointsHistory, Long> {
    List<PointsHistory> findByUserId(Long userId);
    List<PointsHistory> findBySessionId(Long sessionId);
    @Query("SELECT new com.example.dto.LeaderboardEntry(ph.userId, SUM(ph.pointsEarned)) " +
            "FROM PointsHistory ph WHERE ph.testId = :testId " +
            "GROUP BY ph.userId ORDER BY SUM(ph.pointsEarned) DESC")
    List<LeaderboardEntry> findTestLeaderboard(@Param("testId") Long testId);
}