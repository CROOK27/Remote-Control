package com.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private Long userId;
    private String type;
    private String title;
    private String message;
    private String link;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
}