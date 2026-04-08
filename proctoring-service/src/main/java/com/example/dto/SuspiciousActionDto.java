package com.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuspiciousActionDto {
    private Long id;
    private Long sessionId;
    private Long userId;
    private String actionType;
    private String description;
    private String severity;
    private LocalDateTime detectedAt;
    private Boolean resolved;
}
