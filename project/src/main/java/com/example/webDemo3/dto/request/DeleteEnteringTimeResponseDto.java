package com.example.webDemo3.dto.request;

import com.example.webDemo3.dto.MessageDTO;
import lombok.Data;

import java.util.List;

@Data
public class DeleteEnteringTimeResponseDto {
    private List<Integer> listEnteringTime;
    MessageDTO message;
}
