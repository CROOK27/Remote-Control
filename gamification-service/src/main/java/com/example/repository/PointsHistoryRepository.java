package com.example.repository;


import com.example.entity.PointsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PointsHistoryRepository extends JpaRepository<PointsHistory, Long> {
    List<PointsHistory> findByUserId(Long userId);
    List<PointsHistory> findBySessionId(Long sessionId);
}