package com.example.webDemo3.service.manageSchoolRank;

import com.example.webDemo3.dto.manageSchoolRankResponseDto.RankWeekListResponseDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ViewWeekAndClassListResponseDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ViewWeekListResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.SearchRankWeekRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ViewWeekByYearRequestDto;

/*
kimpt142 - 21/07
 */
public interface ViewSchoolRankWeekService {
    ViewWeekAndClassListResponseDto loadRankWeekPage(ViewWeekByYearRequestDto model);
    RankWeekListResponseDto searchRankWeekByWeekAndClass(SearchRankWeekRequestDto model);
    ViewWeekListResponseDto getWeekListByYearId(ViewWeekByYearRequestDto model);
}
