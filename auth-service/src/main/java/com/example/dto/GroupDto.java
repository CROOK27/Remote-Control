package com.example.dto;

import lombok.Data;

@Data
public class GroupDto {
    private Long id;
    private String name;
    private Long specialtyId;
    private String specialtyName;
    private Integer yearOfStudy;
    private String academicYear;
    private Integer studentCount;
}
