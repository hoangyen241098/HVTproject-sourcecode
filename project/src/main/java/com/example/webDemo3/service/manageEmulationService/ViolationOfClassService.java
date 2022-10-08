package com.example.webDemo3.service.manageEmulationService;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewViolationClassListResponseDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.DeleteRequestChangeViolationClassRequestDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.EditViolationOfClassRequestDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.ViewViolationOfClassRequestDto;

import java.io.ByteArrayInputStream;

/*
kimpt142 - 16/07
 */
public interface ViolationOfClassService {
    ByteArrayInputStream download(ViewViolationOfClassRequestDto model);
    ViewViolationClassListResponseDto getViolationOfClasses(ViewViolationOfClassRequestDto model);
    ViewViolationClassListResponseDto getViolationOfAClass(ViewViolationOfClassRequestDto model);
    MessageDTO editViolationOfClass(EditViolationOfClassRequestDto model);
    MessageDTO deleteRequestChange(DeleteRequestChangeViolationClassRequestDto model);
}
