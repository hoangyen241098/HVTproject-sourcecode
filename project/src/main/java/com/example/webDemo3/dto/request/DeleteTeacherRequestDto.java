package com.example.webDemo3.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class DeleteTeacherRequestDto {
    private List<Integer> listTeacher;
}
