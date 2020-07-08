package com.example.webDemo3.service;

import com.example.webDemo3.dto.ViewAssignTaskResponseDto;
import com.example.webDemo3.dto.request.ViewAssignTaskRequestDto;

public interface TaskService {
    public ViewAssignTaskResponseDto viewTask(ViewAssignTaskRequestDto assignTaskRequestDto);
}
