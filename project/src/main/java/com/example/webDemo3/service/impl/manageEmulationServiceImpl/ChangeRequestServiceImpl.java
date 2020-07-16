package com.example.webDemo3.service.impl.manageEmulationServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.ChangeRequestDto;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.entity.ViolationClass;
import com.example.webDemo3.entity.ViolationClassRequest;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.repository.ViolationClassRepository;
import com.example.webDemo3.repository.ViolationClassRequestRepository;
import com.example.webDemo3.service.manageEmulationService.ChangeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * lamnt98
 * 16/07
 */
@Service
public class ChangeRequestServiceImpl implements ChangeRequestService {
    @Autowired
    private ViolationClassRepository violationClassRepository;

    @Autowired
    private ViolationClassRequestRepository violationClassRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MessageDTO acceptRequest(ChangeRequestDto changeRequestDto) {
        MessageDTO message = new MessageDTO();

        Long violationClassId = changeRequestDto.getViolationClassId();
        Integer requestId = changeRequestDto.getRequestId();
        Integer typeRequest = changeRequestDto.getTypeRequest();
        Integer quantityNew = null;

        ViolationClass violationClass = null;
        ViolationClassRequest violationClassRequest = null;
        User user = null;

        try{
            //check typeRequest null or not
            if(typeRequest == null){
                message = Constant.TYPE_REQUEST_NULL;
                return message;
            }

            if(typeRequest == 1 ){
                //check violationClassId null or not
                if(violationClassId == null){
                    message = Constant.VIOLATION_CLASS_ID_NULL;
                    return message;
                }
                violationClass = violationClassRepository.findById(violationClassId).orElse(null);
                //check violationClass null or not
                if(violationClass == null){
                    message = Constant.VIOLATION_CLASS_NULL;
                    return message;
                }

                violationClass.setStatus(1);
                violationClassRepository.save(violationClass);
            }

            if(typeRequest == 0){
                //check requestId null or not
                if(requestId == null){
                    message = Constant.REQUEST_ID_NULL;
                    return message;
                }
                violationClassRequest = violationClassRequestRepository.findById(requestId).orElse(null);
                //check violationClassRequest null or not
                if(violationClassRequest == null){
                    message = Constant.VIOLATION_CLASS_NULL;
                    return message;
                }

                quantityNew = violationClassRequest.getQuantityNew();
                violationClassRequest.getViolationClass().setQuantity(quantityNew);
                violationClassRequest.setStatusChange(2);
                violationClassRequestRepository.save(violationClassRequest);
            }

            message = Constant.ACCEPT_REQUEST_SUCCESS;

        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }
        return message;
    }

    @Override
    public MessageDTO rejectRequest(ChangeRequestDto changeRequestDto) {
        MessageDTO message = new MessageDTO();

        Long violationClassId = changeRequestDto.getViolationClassId();
        Integer requestId = changeRequestDto.getRequestId();
        Integer typeRequest = changeRequestDto.getTypeRequest();
        Integer quantityNew = null;

        ViolationClass violationClass = null;
        ViolationClassRequest violationClassRequest = null;
        User user = null;

        try{
            //check typeRequest null or not
            if(typeRequest == null){
                message = Constant.TYPE_REQUEST_NULL;
                return message;
            }
            
            if(typeRequest == 1 ){
                //check violationClassId null or not
                if(violationClassId == null){
                    message = Constant.VIOLATION_CLASS_ID_NULL;
                    return message;
                }
                violationClass = violationClassRepository.findById(violationClassId).orElse(null);
                //check violationClass null or not
                if(violationClass == null){
                    message = Constant.VIOLATION_CLASS_NULL;
                    return message;
                }

                violationClass.setStatus(0);
                violationClassRepository.save(violationClass);
            }

            if(typeRequest == 0){
                //check requestId null or not
                if(requestId == null){
                    message = Constant.REQUEST_ID_NULL;
                    return message;
                }

                violationClassRequest = violationClassRequestRepository.findById(requestId).orElse(null);
                //check violationClassRequest null or not
                if(violationClassRequest == null){
                    message = Constant.VIOLATION_CLASS_NULL;
                    return message;
                }

                violationClassRequest.setStatusChange(1);
                violationClassRequestRepository.save(violationClassRequest);
            }

            message = Constant.REJECT_REQUEST_SUCCESS;

        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }
        return message;
    }
}