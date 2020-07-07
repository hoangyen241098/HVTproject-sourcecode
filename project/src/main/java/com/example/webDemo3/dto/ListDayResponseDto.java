package com.example.webDemo3.dto;

import com.example.webDemo3.entity.Day;
import lombok.Data;

import java.util.List;

@Data
public class ListDayResponseDto {
    private List<Day> listDay;
    private MessageDTO messageDTO;
}
