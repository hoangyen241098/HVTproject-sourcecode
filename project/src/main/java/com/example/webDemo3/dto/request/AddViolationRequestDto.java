package com.example.webDemo3.dto.request;

import lombok.Data;

@Data
public class AddViolationRequestDto {
    private Integer typeId;
    private String description;
    private Float substractGrade;
}
