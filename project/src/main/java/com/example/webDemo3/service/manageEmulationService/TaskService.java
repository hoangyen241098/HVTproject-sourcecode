package com.example.webDemo3.service.manageEmulationService;

import com.example.webDemo3.dto.manageEmulationResponseDto.ListStarClassDateResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewAssignTaskResponseDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.ViewAssignTaskRequestDto;
import com.example.webDemo3.entity.ViolationClassRequest;

import java.util.List;

public interface TaskService {
    public ViewAssignTaskResponseDto viewTask(ViewAssignTaskRequestDto assignTaskRequestDto);
    public ListStarClassDateResponseDto listStarClassDate();
}
