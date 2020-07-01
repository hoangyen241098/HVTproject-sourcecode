package com.example.webDemo3.service;

import com.example.webDemo3.dto.ClassTimeTableResponseDto;
import com.example.webDemo3.dto.ListWeekResponseDto;
import com.example.webDemo3.dto.ListYearAndClassResponseDto;
import com.example.webDemo3.dto.request.ClassTimeTableRequestDto;
import com.example.webDemo3.dto.request.ListWeekRequestDto;

public interface TimeTableService {
    public ListYearAndClassResponseDto getListYearAndClass();
    public ListWeekResponseDto getListWeekByYearId(ListWeekRequestDto listWeekRequestDto);
    public ClassTimeTableResponseDto getClassTimeTable(ClassTimeTableRequestDto classTimeTable);
}
