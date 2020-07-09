package com.example.webDemo3.dto;

import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.Teacher;
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * lamnt98
 * 03/07
 */
@Data
public class TimeTableResponseDto {
    private Date currentDate;
    private List<Date> appyDateList;
    private List<List<MorInforTimeTableDto>> morningTimeTableList;
    private List<List<AfterInforTimeTableDto>> afternoonTimeTableTableList;
    private List<Class> classList;
    private List<Teacher> teacherList;
    private MessageDTO message;
}
