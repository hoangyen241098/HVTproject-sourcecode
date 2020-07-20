package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.manageTimeTableRequestDto.CheckDateRequestDto;
import com.example.webDemo3.service.assignRedStarService.CreateAssignRedStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
@RequestMapping("/api/assignRedStar")
public class AssignRedStarApiController {
    @Autowired
    private CreateAssignRedStarService assignRedStarService;

    @PostMapping("/checkDate")
    public MessageDTO checkDate(@RequestBody CheckDateRequestDto data)
    {
        return assignRedStarService.checkDate(data.getDate());
    }

    @PostMapping("/create")
    public MessageDTO craete(@RequestBody CheckDateRequestDto data)
    {
        return assignRedStarService.create(data.getDate());
    }

}
