package com.example.webDemo3.dto.manageSchoolRankResponseDto;

import lombok.Data;

/*
kimpt142 - 21/07
 */
@Data
public class RankWeekResponseDto {
    private Integer weekId;
    private Integer classId;
    private String className;
    private Float emulationGrade;
    private Float learningGrade;
    private Float movementGrade;
    private Float laborGrade;
    private Float totalGrade;
    private Integer rank;
    private String history;
}
