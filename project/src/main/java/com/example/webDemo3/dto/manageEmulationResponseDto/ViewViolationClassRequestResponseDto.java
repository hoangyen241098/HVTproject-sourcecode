package com.example.webDemo3.dto.manageEmulationResponseDto;

import lombok.Data;

import java.sql.Date;

@Data
public class ViewViolationClassRequestResponseDto {
    private Integer requestId;
    private Date changeDate;
    private String creatBy;
    private Integer status;
    private String reason;
    private Integer quantityNew;
}
