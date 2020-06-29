package com.example.webDemo3.controller;

import com.example.webDemo3.dto.ClassTableResponseDto;
import com.example.webDemo3.dto.GiftedClassResponseDto;
import com.example.webDemo3.service.ClassListService;
import com.example.webDemo3.service.GiftedClassListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class ClassController {

    @Autowired
    private ClassListService classListService;

    @Autowired
    private GiftedClassListService giftedClassListService;

    /**
     * kimpt142
     * 29/6
     * catch request to get class list with table
     * @return reponseDTO with a class list and messagedto
     */
    @PostMapping("/classtable")
    public ClassTableResponseDto getClassTale()
    {
        ClassTableResponseDto responseDto = new ClassTableResponseDto();
        responseDto = classListService.getClassTable();
        return  responseDto;
    }

    /**
     * kimpt142
     * 29/6
     * catch request to get gifted class list
     * @return reponseDTO with a gifted class list and messagedto
     */
    @PostMapping("/giftedclasslist")
    public GiftedClassResponseDto getGiftedClassList()
    {
        GiftedClassResponseDto responseDto = new GiftedClassResponseDto();
        responseDto = giftedClassListService.getGiftedClassList();
        return  responseDto;
    }
}
