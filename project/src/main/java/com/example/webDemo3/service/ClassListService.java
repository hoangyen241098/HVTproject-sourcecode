package com.example.webDemo3.service;

import com.example.webDemo3.dto.ClassInforResponseDto;
import com.example.webDemo3.dto.ClassListResponseDto;
import com.example.webDemo3.dto.ClassTableResponseDto;
import com.example.webDemo3.dto.request.ClassInforRequestDto;

public interface ClassListService {
    ClassListResponseDto getClassList();
    ClassTableResponseDto getClassTable();
    ClassInforResponseDto getClassInfor(ClassInforRequestDto model);
}
