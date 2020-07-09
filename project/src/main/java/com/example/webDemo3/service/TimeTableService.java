package com.example.webDemo3.service;

import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.ClassTimeTableRequestDto;
import com.example.webDemo3.dto.request.TeacherTimeTableRequestDto;

public interface TimeTableService {


    public ViewClassTimeTableResponseDto viewClassTimeTable();

    public ViewTeacherTimeTableReponseDto viewTeacherTimeTable();

    public SearchTimeTableResponseDto searchClassTimeTable(ClassTimeTableRequestDto classTimeTable);

    public SearchTimeTableResponseDto searchTeacherTimeTable(TeacherTimeTableRequestDto teacherTimeTable);

}
