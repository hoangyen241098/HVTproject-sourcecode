package com.example.webDemo3.service;

import com.example.webDemo3.dto.ViewTeaListResponseDto;
import com.example.webDemo3.dto.request.ViewTeaListRequestDto;

public interface ViewTeaListService {
    ViewTeaListResponseDto searchTeacher(ViewTeaListRequestDto viewTeacherList);
}
