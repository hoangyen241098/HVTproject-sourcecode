package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddClassRequestDto;

/**
 * kimpt142 - 29/6
 */
public interface AddClassService {
    MessageDTO addNewClass(AddClassRequestDto model);
}
