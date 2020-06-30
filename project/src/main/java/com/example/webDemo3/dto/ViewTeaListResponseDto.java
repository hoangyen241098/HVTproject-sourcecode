package com.example.webDemo3.dto;

import com.example.webDemo3.entity.Teacher;
import org.springframework.data.domain.Page;

public class ViewTeaListResponseDto {
    private Page<Teacher> teacherList;
    private MessageDTO message;
}
