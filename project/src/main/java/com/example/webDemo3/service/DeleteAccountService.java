package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.DeleteAccountRequestDto;

/**
 * lamnt98 - 27/06
 */
public interface DeleteAccountService {
    MessageDTO deleteAccount(DeleteAccountRequestDto deleteAccount);
}
