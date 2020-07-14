package com.example.webDemo3.dto.manageEmulationResponseDto;

import com.example.webDemo3.entity.ViolationClass;
import com.example.webDemo3.entity.ViolationClassRequest;
import lombok.Data;

import java.sql.Date;

@Data
public class ViolationClassResponseDto {

    private ViolationClass violationClass;
    private ViolationClassRequest violationClassRequest;
    private Integer typeRequest;
}
