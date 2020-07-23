package com.example.webDemo3.service.manageSchoolRankMonthService;


import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListWeekSchoolRankResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.CreateRankMonthRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ListWeekSchoolRankRequestDto;

public interface CreateAndEditSchoolRankMonthService {
    public ListWeekSchoolRankResponseDto loadListWeek(ListWeekSchoolRankRequestDto requestDto);
    public MessageDTO createRankMonth(CreateRankMonthRequestDto requestDto);
}
