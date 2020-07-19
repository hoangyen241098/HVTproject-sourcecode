package com.example.webDemo3.service.impl.manageEmulationServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.ChangeRequestDto;
import com.example.webDemo3.entity.ClassRedStar;
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
import java.util.List;

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
        Integer quantityNew = null;
        Integer quantiy = null;
        String history = "";
        String userName = changeRequestDto.getUserName().trim();

        ViolationClass violationClass = null;
        ViolationClassRequest violationClassRequest = null;
        User user = null;

        try{

            //check requestId null or not
            if(requestId == null){
                message = Constant.REQUEST_ID_NULL;
                return message;
            }

            //check userName empty or not
            if(userName.isEmpty()){
                message = Constant.USERNAME_EMPTY;
                return message;
            }

            user = userRepository.findUserByUsername(userName);

            //check user null or not
            if(user == null){
                message = Constant.USER_NOT_EXIT;
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
            history = violationClassRequest.getViolationClass().getHistory();
            quantiy = violationClassRequest.getViolationClass().getQuantity();
            history = addHistory(history, violationClassRequest.getReason(),userName,quantiy);

            violationClassRequest.getViolationClass().setHistory(history);
            violationClassRequestRepository.save(violationClassRequest);

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
        Integer quantityNew = null;

        ViolationClass violationClass = null;
        ViolationClassRequest violationClassRequest = null;
        User user = null;

        try{

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

            message = Constant.REJECT_REQUEST_SUCCESS;

        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }
        return message;
    }

    public String addHistory(String history, String reason, String username, int number){
        Date date = new Date(System.currentTimeMillis());
        //check history null or not
        if(history == null){
            history =  date + " - " + username + ".\n";
        }else{
            history += "\n" + date + " - " + username + ".\n";
        }
        history += "Lý do: " + reason + ".\n";
        history += " Số lần vi phạm trước thay đổi: " + number + ".\n";
        return  history;
    }

}
