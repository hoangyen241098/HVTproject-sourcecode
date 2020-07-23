package com.example.webDemo3.service.manageSchoolRankMonthService;

import com.example.webDemo3.dto.manageSchoolRankResponseDto.RankMonthListResposeDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ViewMonthListResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.LoadRankMonthResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.SearchRankMonthRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ViewMonthByYearRequestDto;

import java.io.ByteArrayInputStream;

/*
kimpt142 - 23/07
 */
public interface ViewSchoolRankMonthService {
    ViewMonthListResponseDto getMonthListByYearId(ViewMonthByYearRequestDto model);
    RankMonthListResposeDto searchRankMonthByMonthId(SearchRankMonthRequestDto model);
    ByteArrayInputStream downloadRankMonth(SearchRankMonthRequestDto model);
    LoadRankMonthResponseDto loadRankMonthPage(ViewMonthByYearRequestDto model);
}
