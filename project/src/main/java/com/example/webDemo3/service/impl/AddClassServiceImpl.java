package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddClassRequestDto;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.GiftedClass;
import com.example.webDemo3.entity.Role;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.service.AddClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * kimpt142 - 29/6
 */
@Service
public class AddClassServiceImpl implements AddClassService {

    @Autowired
    private ClassRepository classRepository;

    /**
     * kimpt142
     * 29/6
     * add class into db
     * @param model include grade, giftedclassId, mappingname
     * @return message if success or fail
     */
    @Override
    public MessageDTO addNewClass(AddClassRequestDto model) {

        Class addClass = new Class();
        MessageDTO message = new MessageDTO();
        String classIdentifier = model.getClassIdentifier();
        Integer grade = model.getGrade();
        Integer giftedClassId = model.getGiftedClassId();

        if(classIdentifier.trim().equals("")){
            message = Constant.CLASSIDENTIFIER_EMPTY;
            return message;
        }

        if(grade == null){
            message = Constant.GRADE_EMPTY;
            return message;
        }

        if(giftedClassId == null){
            message = Constant.GIFTEDCLASSID_EMPTY;
            return message;
        }

        List<Class> classList = classRepository.findAll();
        for(Class item : classList){
            //check class identifier
            if(item.getClassIdentifier().equalsIgnoreCase(classIdentifier))
            {
                message = Constant.CLASSIDENTIFIER_EXIST;
                return message;
            }
            else{
                //check nameclass include grade and giftedclass exist
                if(item.getGrade() == grade && item.getGiftedClass().getGiftedClassId() == giftedClassId){
                    message = Constant.CLASSNAME_EXIST;
                    return message;
                }
            }
        }

        addClass.setClassIdentifier(classIdentifier);
        addClass.setGrade(grade);
        addClass.setGiftedClass(new GiftedClass(giftedClassId));
        addClass.setStatus(0);

        try {
            classRepository.save(addClass);
        }
        catch (Exception e)
        {
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }

        message = Constant.SUCCESS;
        return message;
    }
}
