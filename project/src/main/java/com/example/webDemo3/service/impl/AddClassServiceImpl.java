package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddClassRequestDto;
import com.example.webDemo3.dto.request.GenerateNameRequestDto;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.GiftedClass;
import com.example.webDemo3.entity.Role;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.AddClassService;
import com.example.webDemo3.service.GenerateAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenerateAccountService generateAccountService;

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

        addClass.setClassIdentifier(classIdentifier);
        addClass.setGrade(grade);
        addClass.setGiftedClass(new GiftedClass(giftedClassId));
        addClass.setStatus(0);

        try {
            classRepository.save(addClass);
            Class saveClass = classRepository.findByClassIdentifier(classIdentifier);
            Integer classId = saveClass.getClassId();
            GenerateNameRequestDto requestDto;
            if(model.getIsRedStar()){
                requestDto = new GenerateNameRequestDto(3, classId);
                for(int i=0;i<2;i++){
                    User userRedStar = new User();
                    String userName = generateAccountService.generateAccountName(requestDto).getUserName();
                    userRedStar.setUsername(userName);
                    userRedStar.setClassSchool(saveClass);
                    userRedStar.setPassword("123@#123a");
                    userRepository.save(userRedStar);
                }
            }
            if(model.getIsMonitor()){
                requestDto = new GenerateNameRequestDto(4, classId);
                String userName = generateAccountService.generateAccountName(requestDto).getUserName();
                User userMonitor = new User();
                userMonitor.setUsername(userName);
                userMonitor.setClassSchool(saveClass);
                userMonitor.setPassword("123@#123a");
                userRepository.save(userMonitor);
            }
        }
        catch (DataIntegrityViolationException e){
            message = Constant.CLASS_EXIST;
            return message;
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
