package com.example.webDemo3.dto;

import com.example.webDemo3.entity.SchoolWeek;
import com.example.webDemo3.entity.SchoolYear;
import lombok.Data;

import java.util.List;

/**
 * lamnt98
 * 03/07
 */
@Data
public class ListYearAndWeekResponseDto {
    private Integer yearIdCurrent;
    private Integer weekIdCurrent;
    private List<SchoolYear> listYear;
    private List<SchoolWeek> listWeek;
    private MessageDTO messageDTO;
}