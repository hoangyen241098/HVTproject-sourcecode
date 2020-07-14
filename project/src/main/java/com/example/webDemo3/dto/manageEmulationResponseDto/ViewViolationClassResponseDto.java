package com.example.webDemo3.dto.manageEmulationResponseDto;

import lombok.Data;

import java.sql.Date;

@Data
public class ViewViolationClassResponseDto {
    private Integer violationClassId;
    private Integer classId;
    private String note;
    private Integer quantity;
    private String dayName;
    private String description;
    private Date createDate;

    private ViewViolationClassRequestResponseDto viewViolationClassRequestResponseDto;
    private Integer typeRequest;
}
