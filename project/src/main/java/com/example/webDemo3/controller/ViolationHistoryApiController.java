package com.example.webDemo3.controller;

import com.example.webDemo3.dto.request.ViolationHistoryResquestDTO;
import com.example.webDemo3.entity.ViolationClass;
import com.example.webDemo3.service.ViolationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/violationClass/history")
public class ViolationHistoryApiController {

    @Autowired
    private ViolationHistoryService violationHistoryService;

    @PostMapping("/view")
    public List<ViolationClass> deleteGiftedClass(@RequestBody ViolationHistoryResquestDTO model)
    {

        return violationHistoryService.getHistoryViolationOfClas(model);
    }
}
