package com.example.webDemo3.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequestDto {
    private String userName;
    private String oldPassword;
    private String newPassword;
}
