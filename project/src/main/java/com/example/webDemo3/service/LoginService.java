package com.example.webDemo3.service;

import com.example.webDemo3.dto.LoginDto;
import com.example.webDemo3.entity.User;

public interface LoginService {
    LoginDto checkLoginUser(User u);
}
