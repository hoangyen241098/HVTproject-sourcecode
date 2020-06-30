package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.EditClassRequestDto;

/*
kimpt142 - 30/6
 */
public interface EditClassService {
    MessageDTO editClass(EditClassRequestDto model);
}
