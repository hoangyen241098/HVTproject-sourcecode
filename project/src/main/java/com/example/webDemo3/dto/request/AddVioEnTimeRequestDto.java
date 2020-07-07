package com.example.webDemo3.dto.request;

import lombok.Data;

import java.util.List;

/**
 * lamnt98
 * 07/07
 */
@Data
public class AddVioEnTimeRequestDto {
    private  Integer roleId;
    private List<Integer> listDayId;
    private String startTime;
    private String endTime;
}
