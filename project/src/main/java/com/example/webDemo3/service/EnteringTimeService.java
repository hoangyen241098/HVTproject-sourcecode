package com.example.webDemo3.service;

import com.example.webDemo3.dto.ListDayResponseDto;
import com.example.webDemo3.dto.ListEnteringTimeResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.ViolationEnteringTimeResponseDto;
import com.example.webDemo3.dto.request.AddVioEnTimeRequestDto;
import com.example.webDemo3.dto.request.DeleteEnteringTimeRequestDto;

import java.util.List;

public interface EnteringTimeService {
    public ListDayResponseDto getAllDay();
    public MessageDTO deleteEnteringTime(DeleteEnteringTimeRequestDto deleteEnteringTime);
    public ListEnteringTimeResponseDto getListEnteringTime();
    public MessageDTO addEnteringTime(AddVioEnTimeRequestDto addVioEnTimeRequestDto);
}
