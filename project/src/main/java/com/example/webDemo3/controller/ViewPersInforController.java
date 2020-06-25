package com.example.webDemo3.controller;

import com.example.webDemo3.dto.LoginResponseDto;
import com.example.webDemo3.dto.ViewPerInforResponseDto;
import com.example.webDemo3.dto.request.LoginRequestDto;
import com.example.webDemo3.dto.request.ViewPerInforRequestDto;
import com.example.webDemo3.service.ViewPerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class ViewPersInforController {
    @Autowired
    private ViewPerInfoService viewPerInfoService;

    /**
     * lamnt98
     * 25/06
     * catch request from client to find information of user
     * @param model
     * @return ViewPerInforResponseDto
     */
    @PostMapping("/viewinformation")
    public ViewPerInforResponseDto viewInformation(@RequestBody ViewPerInforRequestDto model)
    {
        ViewPerInforResponseDto responseDto = viewPerInfoService.getUserInformation(model);
        return responseDto;
    }

}
