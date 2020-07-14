package com.example.webDemo3.dto.manageEmulationResponseDto;

import com.example.webDemo3.dto.MessageDTO;
import lombok.Data;

import java.util.List;

/**
 * lamnt98
 * 07/07
 */
@Data
public class ViewAssignTaskResponseDto {
    List<ClassRedStarResponseDto> listAssignTask;
    private MessageDTO message;
}
