package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.EditClassRequestDto;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.EditClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/*
kimpt142 - 30/6
 */
@Service
public class EditClassServiceImpl implements EditClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * kimpt142
     * 30/6
     * edit the information of a class
     * @param model include classidentifier, status
     * @return message
     */
    @Override
    public MessageDTO editClass(EditClassRequestDto model) {
        Integer classId = model.getClassId();
        String classIdentifier = model.getClassIdentifier().trim();
        Integer status = model.getStatus();
        MessageDTO message = new MessageDTO();

        if(classIdentifier.equalsIgnoreCase("")){
            message = Constant.CLASSIDENTIFIER_EMPTY;
            return message;
        }

        Class editClass = classRepository.findByClassId(classId);
        if(editClass == null){
            message = Constant.CLASS_NOT_EXIST;
            return message;
        }

        Class classByNewIdetifier = classRepository.findByClassIdentifier(classIdentifier);
        if(!classIdentifier.equalsIgnoreCase(editClass.getClassIdentifier()) && classByNewIdetifier != null){
            message = Constant.CLASSIDENTIFIER_EXIST;
            return message;
        }

        try {
            editClass.setClassIdentifier(classIdentifier);
            if (status != null) {
                editClass.setStatus(status);
                List<User> userList = userRepository.findAllByClassSchoolClassId(classId);
                if (userList != null) {
                    for (User item : userList) {
                        item.setStatus(status);
                        userRepository.save(item);
                    }
                }
            }
            classRepository.save(editClass);
        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }
        message = Constant.SUCCESS;
        return message;
    }
}
