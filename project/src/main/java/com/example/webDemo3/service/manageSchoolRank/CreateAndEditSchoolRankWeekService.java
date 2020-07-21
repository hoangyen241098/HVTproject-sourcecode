package com.example.webDemo3.service.manageSchoolRank;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListDateResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.CreateRankWeekRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.EditRankWeekRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ViewWeekAnDateListRequestDto;

public interface CreateAndEditSchoolRankWeekService {
    public ListDateResponseDto loadListDate();
    public MessageDTO createRankWeek(CreateRankWeekRequestDto requestDto);
    public ListDateResponseDto loadEditListDate(ViewWeekAnDateListRequestDto requestDto);
    public MessageDTO editRankWeek(EditRankWeekRequestDto requestDto);
}
