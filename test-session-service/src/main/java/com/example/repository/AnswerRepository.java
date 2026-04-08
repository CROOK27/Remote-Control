package com.example.repository;


import com.example.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findBySessionId(Long sessionId);
    Optional<Answer> findBySessionIdAndQuestionId(Long sessionId, Long questionId);
    long countBySessionId(Long sessionId);
}
