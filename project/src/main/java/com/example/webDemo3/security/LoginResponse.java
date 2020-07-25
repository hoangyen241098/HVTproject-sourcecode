package com.example.webDemo3.security;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageAccountResponseDto.LoginResponseDto;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
//    private LoginResponseDto loginResponseDto;
    private MessageDTO message;
    private Integer roleid;
    private Integer currentYearId;

    public LoginResponse(){}

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginResponse(MessageDTO message, Integer roleid, Integer currentYearId) {
        this.message = message;
        this.roleid = roleid;
        this.currentYearId = currentYearId;
    }
}
