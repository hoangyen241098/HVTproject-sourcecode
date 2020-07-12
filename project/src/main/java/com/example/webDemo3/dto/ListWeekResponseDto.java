package com.example.webDemo3.dto;

import com.example.webDemo3.entity.SchoolWeek;
import com.example.webDemo3.entity.TimeTableWeek;
import lombok.Data;

import java.util.List;

/**
 * lamnt98
 * 01/07
 */
@Data
public class ListWeekResponseDto {
    private List<TimeTableWeek> listWeek;
    private MessageDTO messageDTO;
}
