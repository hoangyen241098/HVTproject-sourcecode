package com.example.webDemo3.dto.manageSchoolRankResponseDto;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageClassResponseDto.ClassResponseDto;
import com.example.webDemo3.entity.SchoolWeek;
import lombok.Data;
import java.util.List;

/*
kimpt142 - 21/07
 */
@Data
public class ViewWeekAndClassListResponseDto {
    private List<SchoolWeek>  schoolWeekList;
    private List<ClassResponseDto> classList;
    private MessageDTO message;
}
