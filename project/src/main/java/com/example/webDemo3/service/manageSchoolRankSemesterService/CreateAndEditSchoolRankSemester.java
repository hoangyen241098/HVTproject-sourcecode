package com.example.webDemo3.service.manageSchoolRankSemesterService;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListMonthSchoolRankResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.CreateRankSemesterRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ListMonthSchoolRankRequestDto;

public interface CreateAndEditSchoolRankSemester {
    public ListMonthSchoolRankResponseDto loadListMonth(ListMonthSchoolRankRequestDto requestDto);
    public MessageDTO createRankSemester(CreateRankSemesterRequestDto requestDto);
}
