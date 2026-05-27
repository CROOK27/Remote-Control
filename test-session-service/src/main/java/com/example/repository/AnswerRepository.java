package com.example.repository;


import com.example.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findBySessionId(Long sessionId);
    Optional<Answer> findBySessionIdAndQuestionId(Long sessionId, Long questionId);
    long countBySessionId(Long sessionId);

    @Query("SELECT COALESCE(SUM(a.pointsEarned), 0) FROM Answer a WHERE a.session.id = :sessionId")
    int sumPointsBySessionId(Long sessionId);

    @Query("SELECT a.questionId FROM Answer a WHERE a.session.id = :sessionId")
    List<Long> findQuestionIdsBySessionId(Long sessionId);

    @Query("SELECT a FROM Answer a WHERE a.session.id = :sessionId ORDER BY a.answeredAt DESC")
    List<Answer> findBySessionIdOrderByAnsweredAtDesc(Long sessionId);
}
