package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.*;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.GiftedClass;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.repository.GiftedClassRepository;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.ClassService;
import com.example.webDemo3.service.GenerateAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GiftedClassRepository giftedClassRepository;

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
    public AddClassResponseDto addNewClass(AddClassRequestDto model) {
        AddClassResponseDto responseDto = new AddClassResponseDto();
        List<User> userList = new ArrayList<>();
        Class addClass = new Class();
        MessageDTO message = new MessageDTO();
        String classIdentifier = model.getClassIdentifier();
        Integer grade = model.getGrade();
        Integer giftedClassId = model.getGiftedClassId();

        if(classIdentifier.trim().equals("")){
            message = Constant.CLASSIDENTIFIER_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
        }

        if(grade == null){
            message = Constant.GRADE_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
        }

        if(giftedClassId == null){
            message = Constant.GIFTEDCLASSID_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
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
                    userList.add(userRedStar);
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
                userList.add(userMonitor);
            }
        }
        catch (DataIntegrityViolationException e){
            message = Constant.CLASS_EXIST;
            responseDto.setMessage(message);
            return responseDto;
        }
        catch (Exception e)
        {
            message.setMessageCode(1);
            message.setMessage(e.toString());
            responseDto.setMessage(message);
            return responseDto;
        }

        message = Constant.SUCCESS;
        responseDto.setMessage(message);
        responseDto.setUserList(userList);
        return responseDto;
    }


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
