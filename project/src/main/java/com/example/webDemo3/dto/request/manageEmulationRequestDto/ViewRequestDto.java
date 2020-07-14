package com.example.webDemo3.dto.request.manageEmulationRequestDto;

import lombok.Data;

import java.sql.Date;

@Data
public class ViewRequestDto {
    private Integer typeRequest;
    private Integer classId;
    private Integer status;
    private Date createDate;
    private Integer pageNumber;
}
