package com.example.webDemo3.service.manageSchoolRank;

import com.example.webDemo3.dto.manageSchoolRankResponseDto.RankWeekListResponseDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ViewWeekAndClassListResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.SearchRankWeekRequestDto;

/*
kimpt142 - 21/07
 */
public interface ViewSchoolRankWeekService {
    ViewWeekAndClassListResponseDto getWeekAndClassList();
    RankWeekListResponseDto searchRankWeekByWeekAndClass(SearchRankWeekRequestDto model);
}
