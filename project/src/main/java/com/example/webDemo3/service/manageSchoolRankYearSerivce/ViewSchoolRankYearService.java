package com.example.webDemo3.service.manageSchoolRankYearSerivce;

import com.example.webDemo3.dto.manageSchoolRankResponseDto.RankYearListResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.LoadByYearIdRequestDto;

import java.io.ByteArrayInputStream;

/*
kimpt142 - 24/07
 */
public interface ViewSchoolRankYearService {
    RankYearListResponseDto searchRankYearById(LoadByYearIdRequestDto model);
    ByteArrayInputStream downloadRankYear(LoadByYearIdRequestDto model);
}
