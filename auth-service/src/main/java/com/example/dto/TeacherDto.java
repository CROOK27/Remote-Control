package com.example.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TeacherDto {
    private Long id;
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private String position;
    private LocalDate hireDate;
}
