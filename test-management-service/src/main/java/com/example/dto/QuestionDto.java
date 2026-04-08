package com.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private Long id;
    private String text;
    private String questionType;
    private Integer points;
    private Integer sortOrder;
    private List<OptionDto> options;
    private List<MatchingPairDto> matchingPairs;
}