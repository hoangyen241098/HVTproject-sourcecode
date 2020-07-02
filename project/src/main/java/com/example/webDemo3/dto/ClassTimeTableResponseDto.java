package com.example.webDemo3.dto;

import com.example.webDemo3.entity.TimeTable;
import lombok.Data;

import java.util.List;

@Data
public class ClassTimeTableResponseDto {

    private List<TimeTable> morningTimeTable;
    private List<TimeTable> afternoonTimeTable;
    private MessageDTO messageDTO;
}
