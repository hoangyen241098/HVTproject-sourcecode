package com.example.webDemo3.service.manageSchoolRank;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListDateResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.CreateRankWeekRequestDto;

public interface CreateAndEditSchoolRankWeekService {
    public ListDateResponseDto loadListDate();
    public MessageDTO createRankWeek(CreateRankWeekRequestDto requestDto);
}
