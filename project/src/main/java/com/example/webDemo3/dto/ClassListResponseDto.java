package com.example.webDemo3.dto;

import lombok.Data;

import java.util.List;

/**
 * kimpt142 - 28/6
 */
@Data
public class ClassListResponseDto {
    private List<ClassResponseDto> classList;
    private MessageDTO message;
}
