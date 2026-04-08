package com.example.repository;


import com.example.entity.SessionStatus;
import com.example.entity.TestSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface TestSessionRepository extends JpaRepository<TestSession, Long> {
    List<TestSession> findByUserId(Long userId);
    List<TestSession> findByTestId(Long testId);
    List<TestSession> findByUserIdAndStatus(Long userId, SessionStatus status);
    List<TestSession> findByStatusAndStartedAtBefore(SessionStatus status, LocalDateTime time);

    @Modifying
    @Transactional
    @Query("UPDATE TestSession s SET s.status = :status, s.completedAt = CURRENT_TIMESTAMP WHERE s.id = :sessionId")
    void updateStatus(Long sessionId, SessionStatus status);

    boolean existsByUserIdAndTestIdAndStatus(Long userId, Long testId, SessionStatus status);
}
