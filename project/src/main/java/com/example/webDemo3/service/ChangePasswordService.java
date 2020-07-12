package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.ChangePasswordRequestDto;

public interface ChangePasswordService {
    MessageDTO checkChangePasswordUser(ChangePasswordRequestDto user);
}
