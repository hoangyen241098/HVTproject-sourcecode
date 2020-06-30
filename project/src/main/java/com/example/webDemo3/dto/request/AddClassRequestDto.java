package com.example.webDemo3.dto.request;

import lombok.Data;

/**
 * kimpt142 - 29/6
 */
@Data
public class AddClassRequestDto {
    private String classIdentifier;
    private Integer grade;
    private Integer giftedClassId;
}
