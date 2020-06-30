package com.example.webDemo3.service.impl;

import com.example.webDemo3.dto.ViewTeaListResponseDto;
import com.example.webDemo3.repository.TeacherRepository;
import com.example.webDemo3.service.ViewTeaListService;
import org.springframework.beans.factory.annotation.Autowired;

public class ViewTeaListServicempl implements ViewTeaListService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Override
    public ViewTeaListResponseDto searchTeacher(ViewTeaListResponseDto viewTeacherList) {
        return null;
    }
}
