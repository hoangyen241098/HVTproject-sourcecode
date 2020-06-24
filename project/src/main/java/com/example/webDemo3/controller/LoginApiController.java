package com.example.webDemo3.controller;

import com.example.webDemo3.dto.LoginResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.LoginRequestDto;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class LoginApiController {

    @Autowired
    private LoginService loginService;

    /**
     * kimpt142
     * 23/6/2020
     * catch request from client to check login and if success, add username into session
     * @param model is User entity include username and password
     * @return LoginDto with (1,success) if success
     */
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto model,  HttpSession session)
    {
        LoginResponseDto responseDto = loginService.checkLoginUser(model);
        if(responseDto.getMessage().getMessageCode() == 0) {
            session.setAttribute("username", model.getUsername());
        }
        return responseDto;
    }

    /**
     * kimpt142
     * 23/6/2020
     * catch request logout and remove session
     * @param session save username
     * @return message
     */
    @PostMapping("/logout")
    public MessageDTO logout(HttpSession session)
    {
        MessageDTO message = new MessageDTO();
        if(session.getAttribute("username") != null){
            session.removeAttribute("username");
            message.setMessageCode(0);
            message.setMessage("Thành công");
            return message;
        }
        return message;
    }
}
