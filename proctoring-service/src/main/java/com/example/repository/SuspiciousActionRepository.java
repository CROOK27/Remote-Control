package com.example.repository;


import com.example.entity.SuspiciousAction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SuspiciousActionRepository extends JpaRepository<SuspiciousAction, Long> {
    List<SuspiciousAction> findBySessionId(Long sessionId);
    List<SuspiciousAction> findByUserId(Long userId);
    List<SuspiciousAction> findBySessionIdAndResolved(Long sessionId, Boolean resolved);
}
