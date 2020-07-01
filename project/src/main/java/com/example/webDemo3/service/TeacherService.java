package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.ViewTeaInforResponseDto;
import com.example.webDemo3.dto.ViewTeaListResponseDto;
import com.example.webDemo3.dto.request.*;

public interface TeacherService {
    MessageDTO addTeacher(AddTeacherRequestDto addTeacher);
    MessageDTO deleteTeacher(DeleteTeacherRequestDto deleteTeacher);
    MessageDTO editTeacherInformation(EditTeaInforRequestDto editTeaInforRequestDto);
    ViewTeaListResponseDto searchTeacher(ViewTeaListRequestDto viewTeacherList);
    ViewTeaInforResponseDto viewTeacherInfor(ViewTeaInforRequestDto viewTeaInforRequestDto);
}
