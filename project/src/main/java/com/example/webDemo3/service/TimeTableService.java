package com.example.webDemo3.service;

import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.ClassTimeTableRequestDto;
import com.example.webDemo3.dto.request.ListWeekRequestDto;
import com.example.webDemo3.dto.request.TeacherTimeTableRequestDto;

public interface TimeTableService {
    public ListYearAndClassResponseDto getListYearAndClass();
    public ListWeekResponseDto getListWeekByYearId(ListWeekRequestDto listWeekRequestDto);
    public ClassTimeTableResponseDto getClassTimeTable(ClassTimeTableRequestDto classTimeTable);

    public ListYearAndTeacherResponseDto getListYearAndTeacher();
    public TeacherTimeTableResponseDto getTeacherTimeTable(TeacherTimeTableRequestDto teacherTimeTable);
}
