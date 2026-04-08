package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question_snapshots")
@Data
@NoArgsConstructor
public class QuestionSnapshot {

    @Id
    private Long id;

    @Column(name = "test_id", nullable = false)
    private Long testId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "question_type")
    private String questionType;

    private Integer points;

    @Column(columnDefinition = "TEXT")
    private String optionsJson; // JSON с вариантами ответов

    @Column(name = "correct_answer")
    private String correctAnswer;
}
