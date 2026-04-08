package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matching_pairs")
@Data
@NoArgsConstructor
public class MatchingPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String leftItem;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String rightItem;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;
}