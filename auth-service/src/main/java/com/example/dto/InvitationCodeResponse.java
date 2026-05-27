package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitationCodeResponse {
    private String code;
    private Long teacherId;
    private LocalDateTime expiresAt;
}