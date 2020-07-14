package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageClassResponseDto.ClassTableResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewGradingEmulationResponseDto;
import com.example.webDemo3.dto.request.manageClassRequestDto.ClassTableRequestDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.AddViolationForClassRequestDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ListStarClassDateResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewAssignTaskResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewGradingEmulationResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewViolationClassListResponseDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.ViewAssignTaskRequestDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.ViewRequestDto;
import com.example.webDemo3.service.manageEmulationService.TaskService;
import com.example.webDemo3.service.manageEmulationService.GradingEmulationService;
import com.example.webDemo3.service.manageEmulationService.ViewRequestService;
import com.example.webDemo3.service.manageEmulationService.ValidateEmulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
kimpt142 - 14/07
 */
@RestController
@RequestMapping("/api/emulation")
public class EmulationController {

    @Autowired
    private GradingEmulationService gradingEmulationService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ViewRequestService viewRequestService;

    @PostMapping("/liststarclassdate")
    public ListStarClassDateResponseDto getListStarClassDate()
    {
        return taskService.listStarClassDate();
    }

    @PostMapping("/viewassigntask")
    public ViewAssignTaskResponseDto viewAssignTask(@RequestBody ViewAssignTaskRequestDto model)
    {
        return taskService.viewTask(model);
    }

    @PostMapping("/viewrequest")
    public ViewViolationClassListResponseDto viewRequest(@RequestBody ViewRequestDto model)
    {
        return viewRequestService.viewRequest(model);
    }

    /**
     * kimpt142
     * 14/07
     * catch request to get class list and violation list
     * @return reponseDTO with a class list, a violation list and messagedto
     */
    @PostMapping("/viewgradingemulation")
    public ViewGradingEmulationResponseDto viewGradingEmulation()
    {
        ViewGradingEmulationResponseDto responseDto = new ViewGradingEmulationResponseDto();
        responseDto = gradingEmulationService.getClassAndViolationList();
        return responseDto;
    }

    @PostMapping("/addgrademulation")
    public MessageDTO addViolationForClass(@RequestBody AddViolationForClassRequestDto model)
    {
        return gradingEmulationService.addViolationForClass(model);
    }
}
