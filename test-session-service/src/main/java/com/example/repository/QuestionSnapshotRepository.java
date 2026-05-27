package com.example.repository;

import com.example.entity.Answer;
import com.example.entity.QuestionSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionSnapshotRepository extends JpaRepository<QuestionSnapshot, Long> {

    List<QuestionSnapshot> findByTestId(Long testId);
    int countByTestId(Long testId);
}
