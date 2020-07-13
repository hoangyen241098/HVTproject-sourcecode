package com.example.webDemo3.service.manageEmulationService;

import com.example.webDemo3.dto.request.manageEmulationRequestDto.ViolationHistoryResquestDTO;
import com.example.webDemo3.entity.ViolationClass;

import java.util.List;

public interface ViolationHistoryService {
    public List<ViolationClass> getHistoryViolationOfClas(ViolationHistoryResquestDTO model);
}
