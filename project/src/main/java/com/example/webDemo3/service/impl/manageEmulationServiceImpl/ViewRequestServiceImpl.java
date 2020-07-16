package com.example.webDemo3.service.impl.manageEmulationServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewViolationClassListResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViolationClassRequestResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViolationClassResponseDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.ViewRequestDto;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.ViolationClass;
import com.example.webDemo3.entity.ViolationClassRequest;
import com.example.webDemo3.repository.*;
import com.example.webDemo3.service.manageEmulationService.ValidateEmulationService;
import com.example.webDemo3.service.manageEmulationService.ViewRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * lamnt98
 * 14/07
 * Search request of change
 */
@Service
public class ViewRequestServiceImpl implements ViewRequestService {

    @Autowired
    private ValidateEmulationService validateEmulationService;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private ViolationRepository violationRepository;

    @Autowired
    private ViolationClassRepository violationClassRepository;

    @Autowired
    private ViolationClassRequestRepository violationClassRequestRepository;

    @Autowired
    private ClassRepository classRepository;

    @Override
    public ViewViolationClassListResponseDto viewRequest(ViewRequestDto viewRequest) {
        ViewViolationClassListResponseDto responseDto = new ViewViolationClassListResponseDto();
        ViolationClassResponseDto violationClass = new ViolationClassResponseDto();
        List<ViolationClassResponseDto> viewViolationClassList = new ArrayList<>();
        ViolationClassRequestResponseDto violationClassRequest = new ViolationClassRequestResponseDto();

        MessageDTO message = new MessageDTO();
        Page<ViolationClass> pagedResult = null;
        Page<ViolationClassRequest> pagedResultRequest = null;

        Integer typeRequest = viewRequest.getTypeRequest();
        Integer classId = viewRequest.getClassId();
        Integer status = viewRequest.getStatus();
        Date createDate = viewRequest.getCreateDate();
        Class newClass = null;
        Integer pageSize = Constant.PAGE_SIZE;
        Integer pageNumber = viewRequest.getPageNumber();

        Pageable paging;
        try {

            if(classId != null){
                newClass = classRepository.findByClassId(classId);

                //check class exists or not
                if (newClass == null) {
                    message = Constant.CLASS_NOT_EXIST;
                    responseDto.setMessage(message);
                    return responseDto;
                }
            }

            paging = PageRequest.of(pageNumber, pageSize);

            //check typeRequest == 0
            if (typeRequest == 0) {
                pagedResultRequest = getListPageViolationClassRequestWithCondition(createDate, status, classId, paging);

                //check pagedResultRequest null or not
                if(pagedResultRequest != null){
                    //Run to change from ViolationClassEntity và ViolationClassRequest to Dto
                    for(ViolationClassRequest newViolatinClassRequest : pagedResultRequest){
                        violationClassRequest = changeViolationClassRequestFromEntityToDto(newViolatinClassRequest);

                        ViolationClass violationClass1 = violationClassRepository.findById(violationClassRequest.getViolationClassId()).orElse(null);
                        violationClass = changeViolationClassFromEntityToDto(violationClass1);
                        violationClass.setViolationClassRequest(violationClassRequest);
                        violationClass.setTypeRequest(typeRequest);
                        viewViolationClassList.add(violationClass);
                    }
                    responseDto.setViewViolationClassList(viewViolationClassList);
                }
            }

            //check typeRequest == 1
            if (typeRequest == 1) {
                pagedResult = getListPageViolationClassWithCondition(createDate, status, classId, paging);

                //check pagedResult null or not
                if(pagedResult != null){
                    //Run to change from ViolationClassEntity to Dto
                    for(ViolationClass newViolationClass: pagedResult){
                        violationClass = changeViolationClassFromEntityToDto(newViolationClass);
                        violationClass.setTypeRequest(typeRequest);
                        viewViolationClassList.add(violationClass);
                    }
                    responseDto.setViewViolationClassList(viewViolationClassList);
                }
            }

            //check typeRequest == 2
            if (typeRequest == 2) {
                pagedResultRequest = getListPageViolationClassRequestWithCondition(createDate, status, classId, paging);
                pagedResult = getListPageViolationClassWithCondition(createDate, status, classId, paging);

                //check pagedResultRequest null or not
                if(pagedResultRequest != null){
                    //Run to change from ViolationClassEntity và ViolationClassRequest to Dto
                    for(ViolationClassRequest newViolatinClassRequest : pagedResultRequest){
                        violationClassRequest = changeViolationClassRequestFromEntityToDto(newViolatinClassRequest);

                        ViolationClass violationClass1 = violationClassRepository.findById(violationClassRequest.getViolationClassId()).orElse(null);
                        violationClass = changeViolationClassFromEntityToDto(violationClass1);
                        violationClass.setViolationClassRequest(violationClassRequest);
                        violationClass.setTypeRequest(typeRequest);
                        viewViolationClassList.add(violationClass);
                        violationClass.setTypeRequest(0);
                    }
                }

                //check pagedResult null or not
                if(pagedResult != null){
                    //Run to change from ViolationClassEntity to Dto
                    for(ViolationClass newViolationClass: pagedResult){
                        violationClass = changeViolationClassFromEntityToDto(newViolationClass);
                        violationClass.setTypeRequest(typeRequest);
                        viewViolationClassList.add(violationClass);
                        violationClass.setTypeRequest(1);
                    }
                }

                responseDto.setViewViolationClassList(viewViolationClassList);
            }

            //check response empty or not
            if(responseDto.getViewViolationClassList().size() == 0){
                message = Constant.VIEW_CHANGE_REQUEST_NULL;
                responseDto.setMessage(message);
                return  responseDto;
            }

            message = Constant.SUCCESS;
            responseDto.setMessage(message);
        } catch (Exception e) {
            message.setMessageCode(1);
            message.setMessage(e.toString());
            responseDto.setMessage(message);
        }
        return responseDto;
    }

    public Page<ViolationClass> getListPageViolationClassWithCondition(Date createDate, Integer status, Integer classId, Pageable paging) {
        Page<ViolationClass> pagedResult;
        try {
            switch (status) {
                case 0: {
                    pagedResult = violationClassRepository.findViolationClassByConditionAndStatus(classId, createDate, 2, paging);
                    break;
                }
                case 1: {
                    pagedResult = violationClassRepository.findViolationClassByConditionAndStatus(classId, createDate, 1, paging);
                    break;
                }
                case 2: {
                    pagedResult = violationClassRepository.findViolationClassByConditionAndStatus(classId, createDate, 0, paging);
                    break;
                }
                default: {
                    pagedResult = violationClassRepository.findViolationClassByCondition(classId, createDate, paging);
                }
            }
            return  pagedResult;
        } catch (Exception e) {
        }

        return null;
    }

    public Page<ViolationClassRequest> getListPageViolationClassRequestWithCondition(Date createDate, Integer status, Integer classId, Pageable paging) {
        Page<ViolationClassRequest> pagedResultRequest;
        try {
            switch (status) {
                case 0: {
                    pagedResultRequest = violationClassRequestRepository.findViolationClassRequestByConditionAndStatus(classId, createDate, 0, paging);
                    break;
                }
                case 1: {
                    pagedResultRequest = violationClassRequestRepository.findViolationClassRequestByConditionAndStatus(classId, createDate, 2, paging);
                    break;
                }
                case 2: {
                    pagedResultRequest = violationClassRequestRepository.findViolationClassRequestByConditionAndStatus(classId, createDate, 1, paging);
                    break;
                }
                default: {
                    //pagedResultRequest = violationClassRequestRepository.findViolationClassRequestByCondition(classId, createDate, paging);
                    pagedResultRequest = violationClassRequestRepository.findViolationClassRequestByCondition(classId,createDate, paging);

                }
            }
            return pagedResultRequest;
        } catch (Exception e) {
        }

        return null;
    }

    public ViolationClassResponseDto changeViolationClassFromEntityToDto(ViolationClass violationClass){
        ViolationClassResponseDto responseDto = new ViolationClassResponseDto();

        responseDto.setViolationClassId(violationClass.getId());
        responseDto.setClassId(violationClass.getClassId());
        responseDto.setNote(violationClass.getNote());
        responseDto.setQuantity(violationClass.getQuantity());
        responseDto.setDescription(violationClass.getViolation().getDescription());
        responseDto.setCreateDate(violationClass.getDate());
        responseDto.setCreateBy(violationClass.getCreateBy());

        Integer dayId = validateEmulationService.getDayIdByDate(violationClass.getDate());
        String dayName = dayRepository.findByDayId(dayId).getDayName();
        responseDto.setDayName(dayName);

        return responseDto;
    }

    public ViolationClassRequestResponseDto changeViolationClassRequestFromEntityToDto(ViolationClassRequest violationClassRequest){
        ViolationClassRequestResponseDto responseDto = new ViolationClassRequestResponseDto();

        responseDto.setRequestId(violationClassRequest.getRequestId());
        responseDto.setViolationClassId(violationClassRequest.getViolationClass().getId());
        responseDto.setChangeDate(violationClassRequest.getDateChange());
        responseDto.setCreateBy(violationClassRequest.getCreatBy());
        responseDto.setStatus(violationClassRequest.getStatusChange());
        responseDto.setReason(violationClassRequest.getReason());
        responseDto.setQuantityNew(violationClassRequest.getQuantityNew());

        return responseDto;
    }


}

