package com.example.webDemo3.service;

import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.AddClassRequestDto;
import com.example.webDemo3.dto.request.AddGiftedClassRequestDto;
import com.example.webDemo3.dto.request.EditClassRequestDto;

public interface ClassService {
    ClassListResponseDto getClassList();
    ClassTableResponseDto getClassTable();

    GiftedClassResponseDto getGiftedClassList();

    AddClassResponseDto addNewClass(AddClassRequestDto model);

    MessageDTO addGiftedClass(AddGiftedClassRequestDto model);

    MessageDTO editClass(EditClassRequestDto model);
}
