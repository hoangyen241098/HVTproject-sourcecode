package com.example.webDemo3.dto.manageEmulationResponseDto;

import com.example.webDemo3.dto.MessageDTO;
import lombok.Data;

import java.util.List;

@Data
public class ViewRequestResponseDto {

    List<ViewViolationClassResponseDto> viewViolationClassList;
    private MessageDTO message;
}
