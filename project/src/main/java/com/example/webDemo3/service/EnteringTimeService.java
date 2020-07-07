package com.example.webDemo3.service;

import com.example.webDemo3.dto.ListDayResponseDto;
import com.example.webDemo3.dto.ListEnteringTimeResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.ViolationEnteringTimeResponseDto;

import java.util.List;

public interface EnteringTimeService {
    public ListDayResponseDto getAllDay();
    public MessageDTO deleteEnteringTime(List<Integer> listEnteringTime);
    public ListEnteringTimeResponseDto getListEnteringTime();
}
