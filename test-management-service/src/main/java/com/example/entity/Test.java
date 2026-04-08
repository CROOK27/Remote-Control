package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tests")
@Data
@NoArgsConstructor
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @Column(name = "time_limit_minutes")
    private Integer timeLimitMinutes;

    @Column(name = "attempts_allowed")
    private Integer attemptsAllowed = 1;

    @Column(name = "shuffle_questions")
    private Boolean shuffleQuestions = false;

    @Column(name = "shuffle_options")
    private Boolean shuffleOptions = false;

    @Enumerated(EnumType.STRING)
    private TestStatus status = TestStatus.DRAFT;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<Question> questions = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}