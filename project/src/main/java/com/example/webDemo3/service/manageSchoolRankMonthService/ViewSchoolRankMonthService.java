package com.example.webDemo3.service.manageSchoolRankMonthService;

import com.example.webDemo3.dto.manageSchoolRankResponseDto.RankMonthListResposeDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ViewMonthListResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.SearchRankMonthRequestDto;

import java.io.ByteArrayInputStream;

/*
kimpt142 - 23/07
 */
public interface ViewSchoolRankMonthService {
    ViewMonthListResponseDto getMonthList();
    RankMonthListResposeDto searchRankMonthByMonthId(SearchRankMonthRequestDto model);
    ByteArrayInputStream downloadRankMonth(SearchRankMonthRequestDto model);
}
