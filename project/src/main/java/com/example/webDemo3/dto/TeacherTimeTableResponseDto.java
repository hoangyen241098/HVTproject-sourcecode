package com.example.webDemo3.dto;

import com.example.webDemo3.entity.TimeTable;
import lombok.Data;

import java.util.List;

/**
 * lamnt98
 * 02/07
 */
@Data
public class TeacherTimeTableResponseDto {
    private List<TimeTable> morningTimeTable;
    private List<TimeTable> afternoonTimeTable;
    private MessageDTO messageDTO;
}
