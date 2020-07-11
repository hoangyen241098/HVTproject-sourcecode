package com.example.webDemo3.service;

import com.example.webDemo3.dto.request.ViolationHistoryResquestDTO;
import com.example.webDemo3.entity.ViolationClass;

import java.util.List;

public interface ViolationHistoryService {
    public List<ViolationClass> getHistoryViolationOfClas(ViolationHistoryResquestDTO model);
}
