package com.example.webDemo3.dto;

import com.example.webDemo3.entity.SchoolWeek;
import com.example.webDemo3.entity.SchoolYear;
import com.example.webDemo3.entity.Teacher;
import lombok.Data;

import java.util.List;

/**
 * lamnt98
 * 02/07
 */
@Data
public class ListYearAndTeacherResponseDto extends ListYearAndWeekResponseDto {
    private List<Teacher> teacherList;
}
