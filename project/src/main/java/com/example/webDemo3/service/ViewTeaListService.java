package com.example.webDemo3.service;

import com.example.webDemo3.dto.SearchUserResponseDto;
import com.example.webDemo3.dto.ViewTeaListResponseDto;
import com.example.webDemo3.dto.request.SearchUserRequestDto;
import com.example.webDemo3.dto.request.ViewTeaListResquestDto;

public interface ViewTeaListService {
    ViewTeaListResponseDto searchTeacher(ViewTeaListResquestDto viewTeacherList);
}
