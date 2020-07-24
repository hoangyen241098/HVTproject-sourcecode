package com.example.webDemo3.dto.manageSchoolRankResponseDto;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolYearResponseDto.SchoolYearResponseDto;
import com.example.webDemo3.entity.SchoolMonth;
import lombok.Data;
import java.util.List;

/*
kimpt142 - 23/07
 */
@Data
public class LoadRankMonthResponseDto {
    private List<SchoolYearResponseDto> schoolYearList;
    private List<SchoolMonth> schoolMonthList;
    private MessageDTO message;
}