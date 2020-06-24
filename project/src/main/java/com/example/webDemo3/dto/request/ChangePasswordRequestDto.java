package com.example.webDemo3.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequestDto {
    private String username;
    private String oldpassword;
    private String newpassword;
}
