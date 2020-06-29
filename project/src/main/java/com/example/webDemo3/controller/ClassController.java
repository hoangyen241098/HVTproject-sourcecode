package com.example.webDemo3.controller;

import com.example.webDemo3.dto.ClassListResponseDto;
import com.example.webDemo3.dto.ClassTableResponseDto;
import com.example.webDemo3.service.ClassListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class ClassController {

    @Autowired
    private ClassListService classListService;

    /**
     * kimpt142
     * 28/6
     * catch request to get class list
     * @return reponseDTO with a class list and messagedto
     */
    @PostMapping("/classtable")
    public ClassTableResponseDto getClassList()
    {
        ClassTableResponseDto responseDto = new ClassTableResponseDto();
        responseDto = classListService.getClassTable();
        return  responseDto;
    }
}
