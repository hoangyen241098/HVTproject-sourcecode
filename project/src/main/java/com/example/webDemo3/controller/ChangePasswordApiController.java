package com.example.webDemo3.controller;

import com.example.webDemo3.dto.LoginResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.ChangePasswordRequestDto;
import com.example.webDemo3.dto.request.LoginRequestDto;
import com.example.webDemo3.service.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class ChangePasswordApiController {
    @Autowired
    private ChangePasswordService changePasswordService;

    /**
     * lamnt98
     * 23/06
     * catch request from client to change password
     * @param model
     * @return MessageDTO
     */
    @PostMapping("/changepassword")
    public MessageDTO login(@RequestBody ChangePasswordRequestDto model)
    {
        return changePasswordService.checkChangePasswordUser(model);
    }
}
