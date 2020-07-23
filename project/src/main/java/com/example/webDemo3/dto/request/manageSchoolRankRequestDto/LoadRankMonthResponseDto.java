package com.example.webDemo3.dto.request.manageSchoolRankRequestDto;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.SchoolMonthResponseDto;
import com.example.webDemo3.dto.manageSchoolYearResponseDto.SchoolYearResponseDto;
import lombok.Data;
import java.util.List;

/*
kimpt142 - 23/07
 */
@Data
public class LoadRankMonthResponseDto {
    private List<SchoolYearResponseDto> schoolYearList;
    private List<SchoolMonthResponseDto> schoolMonthList;
    private MessageDTO message;
}
