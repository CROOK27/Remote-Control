package com.example.repository;


import com.example.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTestId(Long testId);
    void deleteByTestId(Long testId);
    @Query("SELECT q FROM Question q WHERE q.test.id = :testId ORDER BY q.id")
    List<Question> findByTestIdOrderByOrderIndexAsc(Long testId);
}
