package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;

/**
 * kimpt142 - 27/6
 */
public interface ResetPassService {
    MessageDTO resetMultiplePassword(String[] userNameList, String password);
}
