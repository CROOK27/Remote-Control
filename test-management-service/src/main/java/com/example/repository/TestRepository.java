package com.example.repository;


import com.example.entity.Test;
import com.example.entity.TestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByTeacherId(Long teacherId);
    List<Test> findByStatus(TestStatus status);
    List<Test> findByTeacherIdAndStatus(Long teacherId, TestStatus status);
}