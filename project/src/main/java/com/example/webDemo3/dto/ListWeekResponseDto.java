package com.example.webDemo3.dto;

import com.example.webDemo3.entity.SchoolWeek;
import lombok.Data;

import java.util.List;

/**
 * lamnt98
 * 01/07
 */
@Data
public class ListWeekResponseDto {
    private List<SchoolWeek> listWeek;
    private MessageDTO messageDTO;
}
