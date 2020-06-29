package com.example.webDemo3.service;

import com.example.webDemo3.dto.ClassListResponseDto;
import com.example.webDemo3.dto.ClassTableResponseDto;

public interface ClassListService {
    ClassListResponseDto getClassList();
    ClassTableResponseDto getClassTable();
}
