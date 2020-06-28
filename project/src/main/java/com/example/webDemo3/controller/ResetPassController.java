package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddAccResquestDTO;
import com.example.webDemo3.dto.request.ResetPassRequestDTO;
import com.example.webDemo3.service.AddAccountService;
import com.example.webDemo3.service.ResetPassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * kimpt142 - 27/6
 */
@RestController
@RequestMapping("/api/admin")
public class ResetPassController {

    @Autowired
    private ResetPassService resetPassService;

    /**
     * kimpt142
     * 27/6/2020
     * catch request from client to update new password
     * @param model include a user list and new password
     * @return MessageDTO with (1,success) if success
     */
    @PostMapping("/resetpassword")
    public MessageDTO login(@RequestBody ResetPassRequestDTO model)
    {
        return resetPassService.resetMultiplePassword(model.getUserNameList(), model.getPassWord());
    }
}
