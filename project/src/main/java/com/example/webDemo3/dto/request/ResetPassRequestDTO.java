package com.example.webDemo3.dto.request;

import lombok.Data;

/**
 * kimpt142 - 27/6
 */
@Data
public class ResetPassRequestDTO {
    private String[] userNameList;
    private String passWord;
}
