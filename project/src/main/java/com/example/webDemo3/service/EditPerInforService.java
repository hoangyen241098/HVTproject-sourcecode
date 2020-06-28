package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.EditPerInforRequestDto;

/**
 * lamnt98 - 27/06
 */
public interface EditPerInforService {
    MessageDTO editUserInformation(EditPerInforRequestDto editPerInforRequestDto);
}
