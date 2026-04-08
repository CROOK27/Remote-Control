package com.example.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestPublishedEvent {
    private Long testId;
    private Long teacherId;
    private LocalDateTime publishedAt;
}