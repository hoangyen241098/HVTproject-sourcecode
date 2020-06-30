package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddGiftedClassRequestDto;

/**
 * kimp142 - 29/6
 */
public interface AddGiftedClassService {
    MessageDTO addGiftedClass(AddGiftedClassRequestDto model);
}
