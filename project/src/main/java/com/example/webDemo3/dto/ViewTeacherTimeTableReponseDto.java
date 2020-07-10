package com.example.webDemo3.dto;

import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.Teacher;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class ViewTeacherTimeTableReponseDto {
    private Date currentDate;
    private Integer teacherId;
    private List<Date> appyDateList;
    private List<Teacher> teacherList;
    private List<List<MorInforTimeTableDto>> morningTimeTableList;
    private List<List<AfterInforTimeTableDto>> afternoonTimeTableTableList;
    private MessageDTO message;
}
