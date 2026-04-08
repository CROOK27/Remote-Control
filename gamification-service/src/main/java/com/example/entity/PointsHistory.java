package com.example.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "points_history")
@Data
@NoArgsConstructor
public class PointsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long sessionId;

    @Column(nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private Integer pointsEarned;

    @Column(nullable = false)
    private LocalDateTime earnedAt = LocalDateTime.now();
}