package com.example.webDemo3.service.manageEmulationService;

import com.example.webDemo3.dto.manageEmulationResponseDto.ViewViolationClassListResponseDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.ViewRequestDto;

public interface ViewRequestService {
    public ViewViolationClassListResponseDto viewRequest(ViewRequestDto viewRequest);
}
