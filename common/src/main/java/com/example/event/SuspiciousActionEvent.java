package com.example.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuspiciousActionEvent {
    private Long sessionId;
    private Long userId;
    private String actionType;
    private String details;
    private LocalDateTime timestamp;
}