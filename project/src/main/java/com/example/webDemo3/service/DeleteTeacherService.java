package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.DeleteTeacherRequestDto;

public interface DeleteTeacherService {
    MessageDTO deleteTeacher(DeleteTeacherRequestDto deleteTeacher);
}
