package com.example.webDemo3.controller;

import com.example.webDemo3.dto.ViewAssignTaskResponseDto;
import com.example.webDemo3.dto.request.ViewAssignTaskRequestDto;
import com.example.webDemo3.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/viewassigntask")
    public ViewAssignTaskResponseDto viewAssignTask(@RequestBody ViewAssignTaskRequestDto model)
    {
        return taskService.viewTask(model);
    }
}
