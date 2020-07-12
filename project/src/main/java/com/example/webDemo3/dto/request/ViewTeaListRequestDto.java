package com.example.webDemo3.dto.request;

import lombok.Data;

@Data
public class ViewTeaListRequestDto {
    private String fullName;
    private Integer orderBy;
    private Integer pageNumber;
}
