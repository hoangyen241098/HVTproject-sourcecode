package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.DeleteAccountRequestDto;
import com.example.webDemo3.service.DeleteAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * lamnt98 - 27/06
 */
@RestController
@RequestMapping("/api/admin")
public class DeleteAccountController {
    @Autowired
    private DeleteAccountService deleteAccountService;

    /**
     * lamnt98
     * 27/06
     * catch request from client to delete account
     * @param model
     * @return
     */
    @DeleteMapping("/deleteaccount")
    public MessageDTO login(@RequestBody DeleteAccountRequestDto model)
    {
        return deleteAccountService.deleteAccount(model);
    }
}
