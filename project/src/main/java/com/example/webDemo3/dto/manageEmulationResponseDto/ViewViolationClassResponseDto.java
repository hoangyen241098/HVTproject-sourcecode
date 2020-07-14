package com.example.webDemo3.dto.manageEmulationResponseDto;

import com.example.webDemo3.entity.ViolationClassRequest;
import lombok.Data;

import java.sql.Date;

@Data
public class ViewViolationClassResponseDto {
    private Long violationClassId;
    private Integer classId;
    private String note;
    private Integer quantity;
    private String dayName;
    private String description;
    private Date createDate;

    private ViolationClassRequest violationClassRequest;
    private Integer typeRequest;
}
