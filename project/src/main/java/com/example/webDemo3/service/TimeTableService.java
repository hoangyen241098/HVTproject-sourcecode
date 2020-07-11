package com.example.webDemo3.service;

import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.ClassTimeTableRequestDto;
import com.example.webDemo3.dto.request.TeacherTimeTableRequestDto;

public interface TimeTableService {


    public ListApplyDateAndClassResponseDto getApplyDateAndClassList();

    public ListApplyDateandTeacherResponseDto getApplyDateAndTeacherList();

    public SearchTimeTableResponseDto searchClassTimeTable(ClassTimeTableRequestDto classTimeTable);

    public SearchTimeTableResponseDto searchTeacherTimeTable(TeacherTimeTableRequestDto teacherTimeTable);

}
