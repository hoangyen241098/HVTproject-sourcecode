package com.example.webDemo3.dto;

import com.example.webDemo3.entity.Class;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class SearchTimeTableResponseDto {
    private List<List<MorInforTimeTableDto>> morningTimeTableList;
    private List<List<AfterInforTimeTableDto>> afternoonTimeTableTableList;
    private MessageDTO message;
}
