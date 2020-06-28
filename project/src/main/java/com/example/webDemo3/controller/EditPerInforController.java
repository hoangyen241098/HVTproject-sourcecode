package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.EditPerInforRequestDto;
import com.example.webDemo3.service.EditPerInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * lamnt98 - 27/06
 */
@RestController
@RequestMapping("/api/user")
public class EditPerInforController {
    @Autowired
    private EditPerInforService editPerInforService;

    /**
     * lamnt98
     * 26/06
     * catch request from client to edit information of user
     * @param model
     * @return MessageDTO
     */
    @PostMapping("/editinformation")
    public MessageDTO login(@RequestBody EditPerInforRequestDto model)
    {
        return editPerInforService.editUserInformation(model);
    }
}
