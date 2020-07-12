package com.example.webDemo3.dto.request;

import lombok.Data;

/**
 * kimpt142 - 30/6
 */
@Data
public class EditClassRequestDto {
    private Integer classId;
    private String classIdentifier;
    private Integer status;
}
