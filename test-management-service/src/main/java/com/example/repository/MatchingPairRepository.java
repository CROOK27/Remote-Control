package com.example.repository;

import com.example.entity.MatchingPair;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MatchingPairRepository extends JpaRepository<MatchingPair, Long> {
    List<MatchingPair> findByQuestionId(Long questionId);
    void deleteByQuestionId(Long questionId);
}
