package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "answers")
@Data
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private TestSession session;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "points_earned")
    private Integer pointsEarned = 0;

    @Column(name = "answered_at")
    private LocalDateTime answeredAt = LocalDateTime.now();
}
