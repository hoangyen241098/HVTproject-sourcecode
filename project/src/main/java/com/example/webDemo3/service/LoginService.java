package com.example.webDemo3.service;

import com.example.webDemo3.dto.LoginResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.LoginRequestDto;
import com.example.webDemo3.entity.User;

public interface LoginService {
    LoginResponseDto checkLoginUser(LoginRequestDto u);
}
