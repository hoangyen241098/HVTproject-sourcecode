package com.example.webDemo3.controller;

import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.AddClassRequestDto;
import com.example.webDemo3.dto.request.AddGiftedClassRequestDto;
import com.example.webDemo3.dto.request.ClassInforRequestDto;
import com.example.webDemo3.dto.request.EditClassRequestDto;
import com.example.webDemo3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class ClassController {

    @Autowired
    private ClassListService classListService;

    @Autowired
    private GiftedClassListService giftedClassListService;

    @Autowired
    private AddClassService addClassService;

    @Autowired
    private AddGiftedClassService addGiftedClassService;

    @Autowired
    private EditClassService editClassService;

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

    /**
     * kimpt142
     * 29/6
     * catch request to add new class
     * @return MessageDTO
     */
    @PostMapping("/addclass")
    public AddClassResponseDto addNewClass(@RequestBody AddClassRequestDto model)
    {
        return addClassService.addNewClass(model);
    }

    /**
     * kimpt142
     * 30/6
     * catch request to add new gifted class
     * @return MessageDTO
     */
    @PostMapping("/addgifftedclass")
    public MessageDTO addNewGiftedClass(@RequestBody AddGiftedClassRequestDto model)
    {
        return addGiftedClassService.addGiftedClass(model);
    }

    /**
     * kimpt142
     * 30/6
     * catch request to edit class
     * @return MessageDTO
     */
    @PostMapping("/editclass")
    public MessageDTO editClass(@RequestBody EditClassRequestDto model)
    {
        return editClassService.editClass(model);
    }

    /**
     * kimpt142
     * 1/7
     * catch request to get class infor
     * @return ClassInforResponseDto include identifier, status and message
     */
    @PostMapping("/viewclassinfor")
    public ClassInforResponseDto viewClassInfor(@RequestBody ClassInforRequestDto model)
    {
        return classListService.getClassInfor(model);
    }
}
