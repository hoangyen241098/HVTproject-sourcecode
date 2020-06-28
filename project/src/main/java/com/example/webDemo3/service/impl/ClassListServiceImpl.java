package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.ClassResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.dto.ClassListResponseDto;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.service.ClassListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                String grade = item.getGrade();
                String giftedName = item.getGiftedClass().getName();
                if(!grade.trim().equals("") && !giftedName.trim().equals("")){
                    classDto.setClassName(grade + " " + giftedName);
                }
                else{
                    message = Constant.CLASSNAME_NOT_EXIT;
                    responseDto.setMessage(message);
                    return  responseDto;
                }
                classResList.add(classDto);
            }
            responseDto.setClassList(classResList);
            message = Constant.SUCCESS;
            responseDto.setMessage(message);
            return responseDto;
        }
        message = Constant.CLASSLIST_NOT_EXIT;
        responseDto.setMessage(message);
        return responseDto;
    }
}
