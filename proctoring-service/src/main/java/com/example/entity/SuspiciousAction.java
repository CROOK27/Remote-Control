package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "suspicious_actions")
@Data
@NoArgsConstructor
public class SuspiciousAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long sessionId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 50)
    private String actionType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 20)
    private String severity = "MEDIUM"; // LOW, MEDIUM, HIGH

    @Column(nullable = false)
    private LocalDateTime detectedAt = LocalDateTime.now();

    private Boolean resolved = false;
}
