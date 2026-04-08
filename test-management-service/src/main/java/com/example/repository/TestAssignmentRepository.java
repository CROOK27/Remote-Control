package com.example.repository;

import com.example.entity.TestAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TestAssignmentRepository extends JpaRepository<TestAssignment, Long> {
    List<TestAssignment> findByTestId(Long testId);
    List<TestAssignment> findByGroupId(Long groupId);
    Optional<TestAssignment> findByTestIdAndGroupId(Long testId, Long groupId);
    List<TestAssignment> findByGroupIdAndActive(Long groupId, Boolean active);
}