package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.ListDayResponseDto;
import com.example.webDemo3.dto.ListEnteringTimeResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.ViolationEnteringTimeResponseDto;
import com.example.webDemo3.entity.Day;
import com.example.webDemo3.repository.DayRepository;
import com.example.webDemo3.repository.ViolationEnteringTimeRepository;
import com.example.webDemo3.service.EnteringTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnteringTimeServiceImpl implements EnteringTimeService {
    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private ViolationEnteringTimeRepository enteringTimeRepository;

    @Override
    public ListDayResponseDto getAllDay() {
        ListDayResponseDto listDayResponseDto = new ListDayResponseDto();
        List<Day> listDay = null;
        MessageDTO messageDTO = new MessageDTO();
        try{
            listDay = dayRepository.findAll();
            if(listDay.size() == 0){
                messageDTO = Constant.LIST_DAY_EMPTY;
                listDayResponseDto.setMessageDTO(messageDTO);
                return  listDayResponseDto;
            }
            listDayResponseDto.setListDay(listDay);
            messageDTO = Constant.SUCCESS;
            listDayResponseDto.setMessageDTO(messageDTO);
        }catch (Exception e){
            messageDTO.setMessageCode(1);
            messageDTO.setMessage(e.toString());
            listDayResponseDto.setMessageDTO(messageDTO);
            return  listDayResponseDto;
        }
        return  listDayResponseDto;
    }

    @Override
    public MessageDTO deleteEnteringTime(List<Integer> listEnteringTime) {
        MessageDTO message = new MessageDTO();
        return  message;
    }

    @Override
    public ListEnteringTimeResponseDto getListEnteringTime() {
        ListEnteringTimeResponseDto enteringTimeResponseDto = new ListEnteringTimeResponseDto();
        List<ViolationEnteringTimeResponseDto> listEnteringTime = new ArrayList<>();
        //List<>
        MessageDTO message = new MessageDTO();
        try{

        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            enteringTimeResponseDto.setMessage(message);
            return  enteringTimeResponseDto;
        }
        return  enteringTimeResponseDto;
    }
}
