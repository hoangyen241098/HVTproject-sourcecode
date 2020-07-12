package com.example.webDemo3.service.manageAssignTaskService;

import com.example.webDemo3.dto.manageAssignTaskResponseDto.ViewAssignTaskResponseDto;
import com.example.webDemo3.dto.request.manageAssignTaskRequestDto.ViewAssignTaskRequestDto;

public interface TaskService {
    public ViewAssignTaskResponseDto viewTask(ViewAssignTaskRequestDto assignTaskRequestDto);
}
