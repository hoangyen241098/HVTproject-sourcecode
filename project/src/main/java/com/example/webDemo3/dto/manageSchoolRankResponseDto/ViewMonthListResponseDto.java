package com.example.webDemo3.dto.manageSchoolRankResponseDto;

import com.example.webDemo3.dto.MessageDTO;
import lombok.Data;

import java.util.List;

/*
kimpt142
 */
@Data
public class ViewMonthListResponseDto {
    private List<SchoolMonthResponseDto> schoolMonthList;
    private MessageDTO message;
}
