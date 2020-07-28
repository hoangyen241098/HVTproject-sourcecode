package com.example.webDemo3.dto.request.manageSchoolRankRequestDto;

import com.example.webDemo3.dto.manageSchoolRankResponseDto.SchoolWeekDto;
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * lamnt98
 * 23/07
 */
@Data
public class EditRankMonthRequestDto {
    private Integer monthId;
    private Integer month;
    private String userName;
    private List<SchoolWeekDto> weekList;
    private Date createDate;
}
