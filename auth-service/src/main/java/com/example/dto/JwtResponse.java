package com.example.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    @Column(columnDefinition = "Bearer")
    private String type = "Bearer";
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String role;
    private Long expiresIn;
}