package com.example.webDemo3.service.impl.manageEmulationServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewViolationClassListResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViolationClassRequestResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViolationClassResponseDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.EditViolationOfClassRequestDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.ViewViolationOfClassRequestDto;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.Violation;
import com.example.webDemo3.entity.ViolationClass;
import com.example.webDemo3.entity.ViolationClassRequest;
import com.example.webDemo3.repository.*;
import com.example.webDemo3.service.manageEmulationService.AdditionalFunctionViolationClassService;
import com.example.webDemo3.service.manageEmulationService.ValidateEmulationService;
import com.example.webDemo3.service.manageEmulationService.ViolationOfClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/*
kimpt142 - 16/07
 */
@Service
public class ViolationOfClassServiceImpl implements ViolationOfClassService {

    @Autowired
    private ViolationClassRepository violationClassRepository;

    @Autowired
    private ViolationClassRequestRepository violationClassRequestRepository;

    @Autowired
    private ValidateEmulationService validateEmulationService;

    @Autowired
    private AdditionalFunctionViolationClassService additionalFunctionService;

    /**
     * kimpt142
     * 16/07
     * view violation and request change of a class with user
     * @param model
     * @return
     */
    @Override
    public ViewViolationClassListResponseDto getViolationOfAClass(ViewViolationOfClassRequestDto model) {
        ViewViolationClassListResponseDto responseDto = new ViewViolationClassListResponseDto();
        List<ViolationClassResponseDto> violationClassListDto = new ArrayList<>();
        MessageDTO message = new MessageDTO();
        Integer checkEdit = null;

        String username = model.getUsername();
        Integer classId = model.getClassId();
        Integer roleId = model.getRoleId();
        Date date = model.getDate();

        if(classId == null){
            message = Constant.CLASS_ID_NULL;
            responseDto.setMessage(message);
            return responseDto;
        }

        if(date == null){
            message = Constant.DATE_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
        }

        List<ViolationClass> violationClassRankedList = violationClassRepository.findViolationClassRankedByClassId(classId, date);
        List<ViolationClass> violationClassList = violationClassRepository.findVioClassByClassIdAndAndDate(classId, date);

        if(violationClassRankedList != null && violationClassRankedList.size() != 0){
            for(ViolationClass item : violationClassList){
                ViolationClassResponseDto violationClassRankedDto = new ViolationClassResponseDto();
                violationClassRankedDto = additionalFunctionService.convertViolationClassFromEntityToDto(item);
                violationClassRankedDto.setCheckEdit(1);
                violationClassListDto.add(violationClassRankedDto);
            }
        }

        if (violationClassList != null && violationClassList.size() != 0) {
            for (ViolationClass item : violationClassList) {
                ViolationClassResponseDto violationClassDto = new ViolationClassResponseDto();
                ViolationClassRequestResponseDto violationClassRequestDto = new ViolationClassRequestResponseDto();

                //set violation class into violation class dto
                violationClassDto = additionalFunctionService.convertViolationClassFromEntityToDto(item);

                if(username.equalsIgnoreCase("")){
                    checkEdit = 1;
                    violationClassDto.setCheckEdit(checkEdit);
                    violationClassListDto.add(violationClassDto);
                }
                else {
                    //find request edit in violation request
                    ViolationClassRequest violationClassRequest = violationClassRequestRepository.findNewEditRequest(item.getId(), username);
                    if (violationClassRequest != null && item.getCreateBy().equalsIgnoreCase(username)) {
                        violationClassRequestDto = additionalFunctionService.convertViolationClassRequestFromEntityToDto(violationClassRequest);
                        violationClassDto.setViolationClassRequest(violationClassRequestDto);
                        violationClassDto.setCheckEdit(2);
                    } else {
                        if (validateEmulationService.checkRoleForEditViolationClass(username, roleId, classId, date)) {
                            checkEdit = 0;
                        } else {
                            checkEdit = 1;
                        }
                        violationClassDto.setCheckEdit(checkEdit);
                    }
                    violationClassListDto.add(violationClassDto);
                }
            }
        }


        if(violationClassListDto == null || violationClassListDto.size() == 0){
            message = Constant.VIOLATIONOFCLASS_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
        }

        message = Constant.SUCCESS;
        responseDto.setMessage(message);
        responseDto.setViewViolationClassList(violationClassListDto);
        return responseDto;
    }

    /**
     * kimpt142
     * 16/07
     * edit the violation of a class
     * @param model include infor of violation class
     * @return messagedto
     */
    @Override
    public MessageDTO editViolationOfClass(EditViolationOfClassRequestDto model) {
        MessageDTO message = new MessageDTO();

        String username = model.getUsername();
        Long violationClassId = model.getViolationClassId();
        Integer newQuantity = model.getNewQuantity();
        Date editDate = model.getEditDate();
        Date createDate = model.getCreateDate();
        String reason = model.getReason();
        Integer roleId = model.getRoleId();
        Integer classId = model.getClassId();
        String note = model.getNote();

        if(username.equalsIgnoreCase("")){
            message = Constant.USER_NOT_EXIT;
            return message;
        }

        if(violationClassId == null){
            message = Constant.VIOLATIONCLASSID_EMPTY;
            return message;
        }

        if(newQuantity == null){
            message = Constant.QUANTITY_EMPTY;
            return message;
        }

        if(editDate == null){
            message = Constant.DATE_EMPTY;
            return message;
        }

        if(reason.equalsIgnoreCase("")){
            message = Constant.REASON_EMPTY;
            return message;
        }

        if(roleId == null){
            message = Constant.ROLE_NOT_EXIST;
            return message;
        }

        if(classId == null){
            message = Constant.CLASS_ID_NULL;
            return message;
        }

        try {
            if(!validateEmulationService.checkRankedDateByViolationId(violationClassId)) {
                if(validateEmulationService.checkRoleForEditViolationClass(username, roleId, classId, createDate)){

                    if(roleId == 1 || roleId == 3){
                        ViolationClass violationClass = violationClassRepository.findViolationClassByById(violationClassId);
                        String history = violationClass.getHistory();
                        String newHistory = additionalFunctionService.addHistory(history, reason, username, newQuantity);
                        if (newQuantity == 0) {
                            violationClass.setStatus(0);
                            violationClass.setHistory(newHistory);
                            violationClass.setNote(note);
                            violationClassRepository.save(violationClass);
                        } else {
                            violationClass.setQuantity(newQuantity);
                            violationClass.setHistory(newHistory);
                            violationClass.setNote(note);
                            violationClassRepository.save(violationClass);
                        }
                    }
                    else{
                        ViolationClassRequest violationClassRequest = new ViolationClassRequest();
                        violationClassRequest.setDateChange(editDate);
                        violationClassRequest.setStatusChange(0);
                        violationClassRequest.setCreatBy(username);
                        violationClassRequest.setReason(reason);
                        violationClassRequest.setQuantityNew(newQuantity);
                        violationClassRequest.setViolationClass(new ViolationClass(violationClassId));
                        violationClassRequestRepository.save(violationClassRequest);
                    }
                }
                else{
                    message = Constant.NOT_ACCEPT_EDIT;
                    return message;
                }
            }
        }
        catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }

        message = Constant.SUCCESS;
        return message;
    }
}
