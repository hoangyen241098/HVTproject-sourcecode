package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.ViewTeaListResponseDto;
import com.example.webDemo3.dto.request.AddTeacherRequestDto;
import com.example.webDemo3.dto.request.DeleteTeacherRequestDto;
import com.example.webDemo3.dto.request.EditTeaInforRequestDto;
import com.example.webDemo3.dto.request.ViewTeaListRequestDto;

public interface TeacherService {
    MessageDTO addTeacher(AddTeacherRequestDto addTeacher);
    MessageDTO deleteTeacher(DeleteTeacherRequestDto deleteTeacher);
    MessageDTO editTeacherInformation(EditTeaInforRequestDto editTeaInforRequestDto);
    ViewTeaListResponseDto searchTeacher(ViewTeaListRequestDto viewTeacherList);
}
