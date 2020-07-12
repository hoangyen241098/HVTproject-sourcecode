package com.example.webDemo3.dto;

import lombok.Data;
import java.util.List;

/*
kimpt142 - 06/07
 */
@Data
public class SchoolYearTableResponseDto {
    private List<SchoolYearResponseDto> schoolYearList;
    private MessageDTO message;
}
