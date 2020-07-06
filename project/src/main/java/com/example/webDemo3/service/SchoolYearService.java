package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.SchoolYearTableResponseDto;
import com.example.webDemo3.dto.request.AddSchoolYearRequestDto;
import com.example.webDemo3.dto.request.DelSchoolYearRequestDto;
import com.example.webDemo3.dto.request.EditSchoolYearRequestDto;

/*
kimpt142 - 06/07
 */
public interface SchoolYearService {
    SchoolYearTableResponseDto getSchoolYearTable();
    MessageDTO deleteSchoolYearById(DelSchoolYearRequestDto model);
    MessageDTO addchoolYear(AddSchoolYearRequestDto model);
    MessageDTO editSchoolYear(EditSchoolYearRequestDto model);
}
