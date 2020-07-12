package com.example.webDemo3.dto;

import com.example.webDemo3.entity.Teacher;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ViewTeaListResponseDto {
    private Page<Teacher> teacherList;
    private MessageDTO message;
}
