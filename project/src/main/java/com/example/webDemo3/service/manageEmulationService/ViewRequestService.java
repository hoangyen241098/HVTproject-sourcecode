package com.example.webDemo3.service.manageEmulationService;

import com.example.webDemo3.dto.manageEmulationResponseDto.ViewRequestResponseDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.ViewRequestDto;

public interface ViewRequestService {
    public ViewRequestResponseDto viewRequest(ViewRequestDto viewRequest);
}
