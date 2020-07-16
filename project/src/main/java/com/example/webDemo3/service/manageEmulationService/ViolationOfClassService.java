package com.example.webDemo3.service.manageEmulationService;

import com.example.webDemo3.dto.manageEmulationResponseDto.ViewViolationClassListResponseDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.ViewViolationOfClassRequestDto;

/*
kimpt142 - 16/07
 */
public interface ViolationOfClassService {
    ViewViolationClassListResponseDto getViolationOfAClass(ViewViolationOfClassRequestDto model);
}
