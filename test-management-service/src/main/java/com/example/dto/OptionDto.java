package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionDto {
    private Long id;
    private String text;
    private Boolean isCorrect;
    private Integer sortOrder;
}
