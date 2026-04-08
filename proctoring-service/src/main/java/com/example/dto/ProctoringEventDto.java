package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProctoringEventDto {
    private Long id;
    private Long sessionId;
    private Long userId;
    private String eventType;
    private String details;
    private LocalDateTime eventTime;
    private Boolean isSuspicious;
}
