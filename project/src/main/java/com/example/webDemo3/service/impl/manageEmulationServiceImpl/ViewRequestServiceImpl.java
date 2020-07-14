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
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.repository.ViolationClassRepository;
import com.example.webDemo3.repository.ViolationClassRequestRepository;
import com.example.webDemo3.repository.ViolationRepository;
import com.example.webDemo3.service.manageEmulationService.ViewRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ViewRequestServiceImpl implements ViewRequestService {

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
        ViewViolationClassListResponseDto viewRequestResponseDto = new ViewViolationClassListResponseDto();
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
                    viewRequestResponseDto.setMessage(message);
                    return viewRequestResponseDto;
                }
            }

            paging = PageRequest.of(pageNumber, pageSize);

            //Luôn truyền date và status

            if (typeRequest == 0) {
                pagedResultRequest = getListPageViolationClassRequestWithCondition(createDate, status, classId, paging);
                if(pagedResultRequest != null){
                    for(ViolationClassRequest newViolatinClassRequest : pagedResultRequest){
                        violationClass.setViolationClassRequest(newViolatinClassRequest);
                        viewViolationClassList.add(violationClass);
                    }
                    viewRequestResponseDto.setViewViolationClassList(viewViolationClassList);
                }
            }

            if (typeRequest == 1) {
                pagedResult = getListPageViolationClassWithCondition(createDate, status, classId, paging);
                if(pagedResult != null){
                    for(ViolationClass newViolationClass: pagedResult){
                        violationClass.setViolationClass(newViolationClass);
                        viewViolationClassList.add(violationClass);
                    }
                    viewRequestResponseDto.setViewViolationClassList(viewViolationClassList);
                }
            }

            if (typeRequest == 2) {
                pagedResultRequest = getListPageViolationClassRequestWithCondition(createDate, status, classId, paging);
                pagedResult = getListPageViolationClassWithCondition(createDate, status, classId, paging);

                if(pagedResultRequest != null){
                    for(ViolationClassRequest newViolatinClassRequest : pagedResultRequest){
                        violationClass.setViolationClassRequest(newViolatinClassRequest);
                        viewViolationClassList.add(violationClass);
                    }
                }

                if(pagedResult != null){
                    for(ViolationClass newViolationClass: pagedResult){
                        violationClass.setViolationClass(newViolationClass);
                        viewViolationClassList.add(violationClass);
                    }
                }
                viewRequestResponseDto.setViewViolationClassList(viewViolationClassList);
            }

            if(viewRequestResponseDto.getViewViolationClassList().size() == 0){
                message = Constant.VIEW_CHANGE_REQUEST_NULL;
                viewRequestResponseDto.setMessage(message);
                return  viewRequestResponseDto;
            }

            message = Constant.SUCCESS;
            viewRequestResponseDto.setMessage(message);
        } catch (Exception e) {
            message.setMessageCode(1);
            message.setMessage(e.toString());
            viewRequestResponseDto.setMessage(message);
        }
        return viewRequestResponseDto;
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
                    pagedResult = violationClassRepository.findViolationClassByConditionAndStatus(classId, createDate, 0, paging);
                    break;
                }
                case 2: {
                    pagedResult = violationClassRepository.findViolationClassByConditionAndStatus(classId, createDate, 1, paging);
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
                    pagedResultRequest = violationClassRequestRepository.findViolationClassRequestByCondition(createDate, paging);

                }
            }
            return pagedResultRequest;
        } catch (Exception e) {
        }

        return null;
    }

}

