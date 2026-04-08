package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "proctoring_events")
@Data
@NoArgsConstructor
public class ProctoringEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long sessionId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 50)
    private String eventType; // TAB_SWITCH, WINDOW_BLUR, FOCUS_OUT, PAGE_LEAVE

    @Column(columnDefinition = "TEXT")
    private String details; // JSON с деталями

    @Column(nullable = false)
    private LocalDateTime eventTime = LocalDateTime.now();

    private Boolean isSuspicious = false;
}
