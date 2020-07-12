package com.example.webDemo3.dto.request;

import lombok.Data;

import java.sql.Date;

/**
 * lamnt98
 * 02/07
 */
@Data
public class TeacherTimeTableRequestDto {
    private Date applyDate;
    private Integer teacherId;
}
