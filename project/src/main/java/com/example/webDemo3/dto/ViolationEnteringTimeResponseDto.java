package com.example.webDemo3.dto;

import lombok.Data;

@Data
public class ViolationEnteringTimeResponseDto {
     private Integer violationEnteringTimeId;
     private String roleName;
     private String dayName;
     private String startTime;
     private String endTime;
}
