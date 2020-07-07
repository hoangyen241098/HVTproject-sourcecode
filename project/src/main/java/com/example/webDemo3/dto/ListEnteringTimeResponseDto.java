package com.example.webDemo3.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListEnteringTimeResponseDto {
    private List<ViolationEnteringTimeResponseDto> listEmteringTime;
    private MessageDTO message;
}
