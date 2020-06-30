package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddGiftedClassRequestDto;
import com.example.webDemo3.entity.GiftedClass;
import com.example.webDemo3.repository.GiftedClassRepository;
import com.example.webDemo3.service.AddGiftedClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * kimpt142 - 29/6
 */
@Service
public class AddGiftedClassServiceImpl implements AddGiftedClassService {

    @Autowired
    private GiftedClassRepository giftedClassRepository;

    /**
     * kimpt142
     * 30/6
     * add new gifted class to db
     * @param model include name
     * @return MessageDTO
     */
    @Override
    public MessageDTO addGiftedClass(AddGiftedClassRequestDto model) {
        GiftedClass gClass = new GiftedClass();
        MessageDTO message = new MessageDTO();
        String giftedClassName = model.getGiftedClassName().trim();

        if(giftedClassName.equalsIgnoreCase("")){
            message = Constant.GIFTEDCLASSID_EMPTY;
            return message;
        }

        List<String> giftedNameList = giftedClassRepository.findAllGiftedNameLower();
        if(!giftedNameList.contains(giftedClassName.toLowerCase())){
            gClass.setName(giftedClassName);
            try {
                giftedClassRepository.save(gClass);
                message = Constant.SUCCESS;
                return message;
            }
            catch (Exception e){
                message.setMessageCode(1);
                message.setMessage(e.toString());
                return message;
            }
        }

        message = Constant.GIFTEDCLASSID_EXIST;
        return message;
    }
}
