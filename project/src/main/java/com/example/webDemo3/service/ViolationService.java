package com.example.webDemo3.service;

import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.*;
import com.example.webDemo3.entity.ViolationType;

import java.util.List;

/**
 * lamnt98
 * 06/07
 */
public interface ViolationService {
    public ListVioAndTypeResponseDto getListViolationAndType();
    public ViewViolationResponseDto getViolationById(ViewViolationRequestDto violationRequestDto);

    public MessageDTO editViolation(EditViolationRequestDto editViolationRequestDto);
    public MessageDTO addViolation(AddViolationRequestDto violationRequestDto);
    public MessageDTO deleteViolation(DeleteViolationRequestDio violationRequestDto);

    public ViewViolationTypeResponseDto getViolationTypeById(ViewViolatoinTypeRequestDto viewViolatoinTypeRequestDto);
    public MessageDTO editViolationType(EditViolationTypeRequestDto violationTypeRequestDto);
    public MessageDTO addViolationType(AddViolationTypeRequestDto violationRequestDto);
    public MessageDTO deleteViolationType(DeleteViolationTypeRequestDto violationRequestDto);

    public ListViolationTypeResponseDto getAllViolationType();
}
