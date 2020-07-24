package com.example.webDemo3.service.manageSchoolRank;

import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.SearchRankWeekRequestDto;
import java.io.ByteArrayInputStream;

/*
kimpt142 - 21/07
 */
public interface DownloadRankService {
    ByteArrayInputStream downloadRankWeek(SearchRankWeekRequestDto model);
}
