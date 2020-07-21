package com.example.webDemo3.service.manageSchoolRank;

import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.SearchRankWeekRequestDto;
import java.io.ByteArrayInputStream;

public interface DownloadRankWeekService {
    ByteArrayInputStream downloadRankWeek(SearchRankWeekRequestDto model);
}
