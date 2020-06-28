package com.example.webDemo3.dto;

import lombok.Data;

@Data
public class ClassResponseDto implements Comparable<ClassResponseDto> {
    private Integer classID;
    private String className;

    @Override
    public int compareTo(ClassResponseDto o) {
        return this.className.compareTo(o.getClassName());
    }
}
