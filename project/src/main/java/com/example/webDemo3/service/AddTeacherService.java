package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddTeacherRequestDto;

public interface AddTeacherService {
    MessageDTO addTeacher(AddTeacherRequestDto addTeacher);
}
