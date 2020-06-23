package com.example.webDemo3.controller;

import com.example.webDemo3.dto.LoginDto;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class LoginApiController {

    @Autowired
    private LoginService loginService;

    /**
     * kimpt142
     * 23/6/2020
     * catch request from client to check login
     * @param model is User entity include username and password
     * @return LoginDto with (1,success) if success
     */
    @PostMapping("/login")
    public LoginDto login(@RequestBody User model)
    {
        return loginService.checkLoginUser(model);
    }
}
