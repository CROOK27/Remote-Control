package com.example.repository;


import com.example.entity.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserPointsRepository extends JpaRepository<UserPoints, Long> {
    Optional<UserPoints> findByUserId(Long userId);
}