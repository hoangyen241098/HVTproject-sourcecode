package com.example.webDemo3.dto;

import com.example.webDemo3.entity.Violation;
import com.example.webDemo3.entity.ViolationType;
import lombok.Data;

import java.util.List;

/**
 * lamnt98
 * 06/07
 */
@Data
public class ListVioAndTypeResponseDto {
    private List<ViolationTypeResponseDto> listViolationType;
    private MessageDTO messageDTO;
}
