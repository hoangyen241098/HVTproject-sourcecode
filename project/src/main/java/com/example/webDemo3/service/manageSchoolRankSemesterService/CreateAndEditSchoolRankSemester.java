package com.example.webDemo3.service.manageSchoolRankSemesterService;

import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListMonthSchoolRankResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ListMonthSchoolRankRequestDto;

public interface CreateAndEditSchoolRankSemester {
    public ListMonthSchoolRankResponseDto loadListWeek(ListMonthSchoolRankRequestDto requestDto);
}
