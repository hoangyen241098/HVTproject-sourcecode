package com.example.webDemo3.service;

import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.*;

public interface ClassService {
    ClassListResponseDto getClassList();

    GiftedClassResponseDto getGiftedClassList();

    AddClassResponseDto addNewClass(AddClassRequestDto model);

    MessageDTO addGiftedClass(AddGiftedClassRequestDto model);

    MessageDTO editClass(EditClassRequestDto model);

    ClassInforResponseDto getClassInfor(ClassInforRequestDto model);

    ClassTableResponseDto getClassTable(ClassTableRequestDto model);
}
