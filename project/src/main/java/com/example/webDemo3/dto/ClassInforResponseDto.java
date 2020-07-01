package com.example.webDemo3.dto;

import lombok.Data;

@Data
public class ClassInforResponseDto {
    private String classIdentifier;
    private Integer status;
    private MessageDTO message;
}
