package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddAccResquestDTO;

/**
 * kimtp142 - 27/6
 */
public interface AddAccountService {
    MessageDTO addAccount(AddAccResquestDTO resquestDTO);
}
