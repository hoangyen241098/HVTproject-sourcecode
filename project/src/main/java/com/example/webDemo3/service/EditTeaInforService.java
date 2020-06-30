package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.EditTeaInforRequestDto;

/**
 * lamnt98
 * 29/06
 */
public interface EditTeaInforService {
    MessageDTO editTeacherInformation(EditTeaInforRequestDto editTeaInforRequestDto);
}
