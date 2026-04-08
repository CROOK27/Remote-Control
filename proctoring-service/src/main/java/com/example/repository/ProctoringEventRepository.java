package com.example.repository;


import com.example.entity.ProctoringEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProctoringEventRepository extends JpaRepository<ProctoringEvent, Long> {
    List<ProctoringEvent> findBySessionId(Long sessionId);
    List<ProctoringEvent> findByUserId(Long userId);
    List<ProctoringEvent> findBySessionIdAndIsSuspicious(Long sessionId, Boolean isSuspicious);
}