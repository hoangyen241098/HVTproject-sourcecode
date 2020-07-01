package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.ClassInforRequestDto;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.service.ClassListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ClassListServiceImpl implements ClassListService {

    @Autowired
    private ClassRepository classRepository;

    /**
     * kimpt142
     * 28/6
     * get list include classid, class name from 2 table CLASSES and GIFTED_CLASSES
     * @return ClassListResponseDto
     */
    @Override
    public ClassListResponseDto getClassList() {
        ClassListResponseDto responseDto = new ClassListResponseDto();
        MessageDTO message = new MessageDTO();
        List<Class> classList = classRepository.findAll();
        List<ClassResponseDto> classResList = new ArrayList<>();

        if(classList!=null)
        {
            for(Class item : classList)
            {
                ClassResponseDto classDto = new ClassResponseDto();
                classDto.setClassID(item.getClassId());
                Integer grade = item.getGrade();
                String giftedName = item.getGiftedClass().getName();
                if(grade != null && !giftedName.trim().equals("")){
                    classDto.setClassName(grade.toString() + " " + giftedName);
                }
                else{
                    message = Constant.CLASSNAME_NOT_EXIT;
                    responseDto.setMessage(message);
                    return  responseDto;
                }
                classResList.add(classDto);
            }
            Collections.sort(classResList);
            responseDto.setClassList(classResList);
            message = Constant.SUCCESS;
            responseDto.setMessage(message);
            return responseDto;
        }
        message = Constant.CLASSLIST_NOT_EXIT;
        responseDto.setMessage(message);
        return responseDto;
    }

    /**
     * kimpt142
     * 29/6
     * get list class to show in detail table
     * @return a detail of class
     */
    @Override
    public ClassTableResponseDto getClassTable() {
        ClassTableResponseDto responseDto = new ClassTableResponseDto();
        MessageDTO message = new MessageDTO();
        List<Class> classList = classRepository.findAll();

        if(classList!=null)
        {
            responseDto.setClassList(classList);
            message = Constant.SUCCESS;
            responseDto.setMessage(message);
            return responseDto;
        }
        message = Constant.CLASSLIST_NOT_EXIT;
        responseDto.setMessage(message);
        return responseDto;
    }

    /**
     * kimpt142
     * 1/7
     * get class information from classId
     * @param model include classId
     * @return class information
     */
    @Override
    public ClassInforResponseDto getClassInfor(ClassInforRequestDto model) {
        ClassInforResponseDto responseDto = new ClassInforResponseDto();
        MessageDTO message = new MessageDTO();

        Class classInfor = classRepository.findByClassId(model.getClassId());
        if(classInfor == null) {
            message = Constant.CLASS_NOT_EXIST;
            responseDto.setMessage(message);
            return responseDto;
        }
        
        responseDto.setClassIdentifier(classInfor.getClassIdentifier());
        responseDto.setStatus(classInfor.getStatus());
        message = Constant.SUCCESS;
        responseDto.setMessage(message);
        return responseDto;
    }
}
