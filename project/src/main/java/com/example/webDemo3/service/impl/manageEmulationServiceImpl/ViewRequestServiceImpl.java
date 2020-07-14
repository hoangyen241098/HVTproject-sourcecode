package com.example.webDemo3.service.impl.manageEmulationServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewRequestResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewViolationClassRequestResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewViolationClassResponseDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.ViewRequestDto;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.Teacher;
import com.example.webDemo3.entity.ViolationClass;
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
    public ViewRequestResponseDto viewRequest(ViewRequestDto viewRequest) {
        ViewRequestResponseDto viewRequestResponseDto = new ViewRequestResponseDto();

        /*ViewViolationClassResponseDto violationClass = new ViewViolationClassResponseDto();
        ViewViolationClassRequestResponseDto violationClassRequest = new ViewViolationClassRequestResponseDto();
        MessageDTO message = new MessageDTO();
        Page<ViolationClass> pagedResult;

        Integer typeRequest = viewRequest.getTypeRequest();
        Integer classId = viewRequest.getClassId();
        Integer status = viewRequest.getStatus();
        Date createDate = viewRequest.getCreateDate();
        String creatBy = viewRequest.getCreatBy();
        Class newClass = null;
        Integer pageSize = Constant.PAGE_SIZE;
        Integer pageNumber = viewRequest.getPageNumber();

        Pageable paging;
        try{
            //check classId null or not
            if(classId == null){
                message = Constant.CLASS_ID_NULL;
                viewRequestResponseDto.setMessage(message);
                return viewRequestResponseDto;
            }

            newClass = classRepository.findByClassId(classId);;

            //check class exists or not
            if(newClass == null){
                message = Constant.CLASS_NOT_EXIST;
                viewRequestResponseDto.setMessage(message);
                return viewRequestResponseDto;
            }

            //check createDate null or not
            if(createDate == null){
                message = Constant.FROMDATE_EMPTY;
                viewRequestResponseDto.setMessage(message);
                return viewRequestResponseDto;
            }

            paging = PageRequest.of(pageNumber, pageSize);
            switch (typeRequest){
                case 0:{

                    break;
                }
                case 1:{
                    switch (status){
                        case 2: {

                        }

                    }
                    pagedResult = violationClassRepository.findByClassIdAndStatus(classId,status);
                    break;
                }
                default:{

                }
            }

        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            viewRequestResponseDto.setMessage(message);
        }*/

        return viewRequestResponseDto;
    }
}
