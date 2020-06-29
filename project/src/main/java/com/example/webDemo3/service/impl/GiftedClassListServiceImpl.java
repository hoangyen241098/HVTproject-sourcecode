package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.GiftedClassResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.entity.GiftedClass;
import com.example.webDemo3.repository.GiftedClassRepository;
import com.example.webDemo3.service.GiftedClassListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * kimpt142 - 29/6
 */
@Service
public class GiftedClassListServiceImpl implements GiftedClassListService {

    @Autowired
    private GiftedClassRepository giftedRepository;

    /**
     * kimpt142
     * 29/6
     * get gifted class list in db
     * @return a gifted class list
     */
    @Override
    public GiftedClassResponseDto getGiftedClassList() {
        GiftedClassResponseDto responseDto = new GiftedClassResponseDto();
        MessageDTO message = new MessageDTO();
        List<GiftedClass> giftedClasses = giftedRepository.findAll();

        if(giftedClasses!=null)
        {
            responseDto.setGiftedClassList(giftedClasses);
            message = Constant.SUCCESS;
            responseDto.setMessage(message);
            return responseDto;
        }
        message = Constant.GIFTEDCLASSLIST_NOT_EXIT;
        responseDto.setMessage(message);
        return responseDto;
    }
}
