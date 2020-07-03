package com.example.webDemo3.dto;

import lombok.Data;

import java.util.List;

/**
 * lamnt98
 * 03/07
 */
@Data
public class TimeTableResponseDto {
    private List<MorInforTimeTableDto> morningTimeTable;
    private List<AfterInforTimeTableDto> afternoonTimeTable;
    private MessageDTO messageDTO;
}
