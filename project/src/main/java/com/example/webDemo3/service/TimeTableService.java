package com.example.webDemo3.service;

import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.ClassTimeTableRequestDto;
import com.example.webDemo3.dto.request.ListWeekRequestDto;
import com.example.webDemo3.dto.request.TeacherTimeTableRequestDto;

public interface TimeTableService {


    public TimeTableResponseDto getClassTimeTable(ClassTimeTableRequestDto classTimeTable);

    public TimeTableResponseDto getTeacherTimeTable(TeacherTimeTableRequestDto teacherTimeTable);

}
