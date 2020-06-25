package com.example.webDemo3.service;

import com.example.webDemo3.dto.ViewPerInforResponseDto;
import com.example.webDemo3.dto.request.ViewPerInforRequestDto;

public interface ViewPerInfoService {
    ViewPerInforResponseDto getUserInformation(ViewPerInforRequestDto userName);
}
