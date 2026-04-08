package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDto {
    private Long id;
    private Long testId;
    private String testTitle;
    private Long groupId;
    private String groupName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;
    private LocalDateTime createdAt;
}
