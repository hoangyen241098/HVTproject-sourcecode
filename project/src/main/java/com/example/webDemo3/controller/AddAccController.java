package com.example.webDemo3.controller;

import com.example.webDemo3.dto.LoginResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddAccResquestDTO;
import com.example.webDemo3.dto.request.LoginRequestDto;
import com.example.webDemo3.service.AddAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/admin")
public class AddAccController {

    @Autowired
    private AddAccountService addAccountService;

    /**
     * kimpt142
     * 27/6/2020
     * catch request from client to add new account
     * @param model is
     * @return LoginDto with (1,success) if success
     */
    @PostMapping("/createaccount")
    public MessageDTO login(@RequestBody AddAccResquestDTO model)
    {
        return addAccountService.addAccount(model);
    }
}
