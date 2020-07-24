package com.example.webDemo3.service.manageSchoolRankYearSerivce;

import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListSemesterSchoolRankResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ViewSemesterOfEditRankYearRequestDto;

public interface CreateAndEditSchoolRankYearService {
    public ListSemesterSchoolRankResponseDto loadListSemester();
    public ListSemesterSchoolRankResponseDto loadEditListSemester(ViewSemesterOfEditRankYearRequestDto requestDto);
}
