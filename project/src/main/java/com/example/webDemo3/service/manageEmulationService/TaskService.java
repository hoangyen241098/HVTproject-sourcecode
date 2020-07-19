package com.example.webDemo3.service.manageEmulationService;

import com.example.webDemo3.dto.manageEmulationResponseDto.ListClassAndDateResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewAssignTaskResponseDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.ViewAssignTaskRequestDto;

public interface TaskService {
    public ViewAssignTaskResponseDto viewTask(ViewAssignTaskRequestDto assignTaskRequestDto);
    public ListClassAndDateResponseDto listStarClassDate();
}
