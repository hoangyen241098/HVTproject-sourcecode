package com.example.webDemo3.dto;

import lombok.Data;
import java.util.List;
import com.example.webDemo3.entity.Class;

/**
 * kimpt142 - 29/6
 */
@Data
public class ClassTableResponseDto {
    private List<Class> classList;
    private MessageDTO message;
}
