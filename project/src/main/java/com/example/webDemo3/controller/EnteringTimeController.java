package com.example.webDemo3.controller;

import com.example.webDemo3.dto.ListDayResponseDto;
import com.example.webDemo3.dto.ListEnteringTimeResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.DeleteEnteringTimeRequestDto;
import com.example.webDemo3.service.EnteringTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class EnteringTimeController {

    @Autowired
    private EnteringTimeService enteringTimeService;

    @PostMapping("/getallday")
    public ListDayResponseDto getAllDay()
    {
        return  enteringTimeService.getAllDay();
    }

    @PostMapping("/viewenteringtime")
    public ListEnteringTimeResponseDto viewEnteringTime()
    {
        return  enteringTimeService.getListEnteringTime();
    }

    @PostMapping("/deleteenteringtime")
    public MessageDTO deleteEnteringTime(@RequestBody DeleteEnteringTimeRequestDto model)
    {
        return  enteringTimeService.deleteEnteringTime(model);
    }
}
