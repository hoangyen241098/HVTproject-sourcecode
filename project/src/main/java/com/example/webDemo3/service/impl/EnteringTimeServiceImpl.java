package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.ListDayResponseDto;
import com.example.webDemo3.dto.ListEnteringTimeResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.ViolationEnteringTimeResponseDto;
import com.example.webDemo3.dto.request.AddVioEnTimeRequestDto;
import com.example.webDemo3.dto.request.DeleteEnteringTimeRequestDto;
import com.example.webDemo3.entity.Day;
import com.example.webDemo3.entity.Role;
import com.example.webDemo3.entity.ViolationEnteringTime;
import com.example.webDemo3.repository.DayRepository;
import com.example.webDemo3.repository.RoleRepository;
import com.example.webDemo3.repository.ViolationEnteringTimeRepository;
import com.example.webDemo3.service.EnteringTimeService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnteringTimeServiceImpl implements EnteringTimeService {
    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private ViolationEnteringTimeRepository enteringTimeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ListDayResponseDto getAllDay() {
        ListDayResponseDto listDayResponseDto = new ListDayResponseDto();
        List<Day> listDay = null;
        MessageDTO messageDTO = new MessageDTO();
        try{
            listDay = dayRepository.findAll();
            if(listDay.size() == 0){
                messageDTO = Constant.LIST_DAY_EMPTY;
                listDayResponseDto.setMessageDTO(messageDTO);
                return  listDayResponseDto;
            }
            listDayResponseDto.setListDay(listDay);
            messageDTO = Constant.SUCCESS;
            listDayResponseDto.setMessageDTO(messageDTO);
        }catch (Exception e){
            messageDTO.setMessageCode(1);
            messageDTO.setMessage(e.toString());
            listDayResponseDto.setMessageDTO(messageDTO);
            return  listDayResponseDto;
        }
        return  listDayResponseDto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class})
    public MessageDTO deleteEnteringTime(DeleteEnteringTimeRequestDto deleteEnteringTime) {
        MessageDTO message = new MessageDTO();
        List<Integer> listEnteringTime = null;
        try{
            listEnteringTime = deleteEnteringTime.getListEnteringTime();
            //check size list entering time
            if(listEnteringTime.size() == 0){
                message = Constant.DELETE_ENTERING_TIME_EMPTY;
                return  message;
            }
            for(Integer enteringTimeId : listEnteringTime){
                ViolationEnteringTime violationEnteringTime = enteringTimeRepository.findById(enteringTimeId).orElse(null);
                //check violation entering time exists or not
                if(violationEnteringTime != null){
                    enteringTimeRepository.delete(violationEnteringTime);
                }else{
                    message = Constant.ENTERING_TIME_EMPTY;
                    return  message;
                }
            }
            message = Constant.DELETE_VIOLATION_ENTERING_TIME_SUCCESS;
        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }
        return message;
    }

    @Override
    public ListEnteringTimeResponseDto getListEnteringTime() {
        ListEnteringTimeResponseDto enteringTimeResponseDto = new ListEnteringTimeResponseDto();
        List<ViolationEnteringTimeResponseDto> listEnteringTime = new ArrayList<>();
        List<ViolationEnteringTime> violationEnteringTimes = new ArrayList<>();
        Integer violationEnteringTimeId;
        String roleName;
        String dayName;
        String startTime;
        String endTime;
        MessageDTO message = new MessageDTO();
        try{
            violationEnteringTimes = enteringTimeRepository.findAll();

            //check violationEnteringTimes empty or not
            if(violationEnteringTimes.size() == 0){
                message = Constant.VIOLATION_ENTERING_TIME_NULL;
                enteringTimeResponseDto.setMessage(message);
            }

            //change violation entering time from entity to Dto
            for(int i = 0; i < violationEnteringTimes.size(); i++){
                ViolationEnteringTime violationEnteringTime = new ViolationEnteringTime();
                ViolationEnteringTimeResponseDto violationEnteringTimeResponseDto = new ViolationEnteringTimeResponseDto();
                violationEnteringTime = violationEnteringTimes.get(i);
                violationEnteringTimeId = violationEnteringTime.getViolationEnteringTimeId();
                roleName = roleRepository.findByRoleId(violationEnteringTime.getRoleId()).getRoleName();
                dayName = dayRepository.findByDayId(violationEnteringTime.getDayId()).getDayName();
                startTime = changeTimeToSrtingFormat(violationEnteringTime.getStartTime());
                endTime = changeTimeToSrtingFormat(violationEnteringTime.getEndTime());

                violationEnteringTimeResponseDto.setViolationEnteringTimeId(violationEnteringTimeId);
                violationEnteringTimeResponseDto.setRoleName(roleName);
                violationEnteringTimeResponseDto.setDayName(dayName);
                violationEnteringTimeResponseDto.setStartTime(startTime);
                violationEnteringTimeResponseDto.setEndTime(endTime);
                listEnteringTime.add(violationEnteringTimeResponseDto);
            }

            //check listEnteringTime empty or not
            if(listEnteringTime.size() == 0){
                message = Constant.VIOLATION_ENTERING_TIME_NULL;
                enteringTimeResponseDto.setMessage(message);
            }

            enteringTimeResponseDto.setListEmteringTime(listEnteringTime);
            message = Constant.SUCCESS;
            enteringTimeResponseDto.setMessage(message);
        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            enteringTimeResponseDto.setMessage(message);
            return  enteringTimeResponseDto;
        }
        return  enteringTimeResponseDto;
    }

    @Override
    public MessageDTO addEnteringTime(AddVioEnTimeRequestDto addVioEnTimeRequestDto) {
        MessageDTO message = new MessageDTO();
        Role role;
        Integer roleId;
        List<Integer> listDayId;
        long startTime;
        long endTime;
        boolean checkListDay = true;
        try{
            roleId = addVioEnTimeRequestDto.getRoleId();
            //check roleId null or not
            if(roleId == null) {
                message = Constant.ROLE_ID_NULL;
                return message;
            }

            role = roleRepository.findByRoleId(roleId);
            //check role exist or not
            if(role == null){
                message = Constant.ROLE_NOT_EXIST;
                return message;
            }

            listDayId = addVioEnTimeRequestDto.getListDayId();
            if(listDayId.size() == 0){
                message = Constant.LIST_DAY_EMPTY;
                return message;
            }
            for(int i = 0; i < listDayId.size(); i++){
                Day day = dayRepository.findByDayId(listDayId.get(i));
                if(day == null){
                    checkListDay = false;
                    break;
                }
            }

            //check all day in list day exist or not
            if(!checkListDay){
                message = Constant.DAY_NOT_EXIST;
                return message;
            }
            startTime = changeStringToTimeFormat(addVioEnTimeRequestDto.getStartTime());
            if(String.valueOf(startTime).trim().isEmpty()){
                message = Constant.START_TIME_EMPTY;
                return message;
            }
            endTime = changeStringToTimeFormat(addVioEnTimeRequestDto.getEndTime());

            if(String.valueOf(endTime).trim().isEmpty()){
                message = Constant.END_TIME_EMPTY;
                return message;
            }
            for(int i = 0; i < listDayId.size(); i++){
                ViolationEnteringTime enteringTime = new ViolationEnteringTime();
                enteringTime.setRoleId(roleId);
                enteringTime.setDayId(listDayId.get(i));
                //enteringTime.setStartTime(startTime);
                //enteringTime.setEndTime(endTime);
            }

        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }
        return message;
    }

    public String changeTimeToSrtingFormat(Time time){
        String newTime = time.toString();
        String endTime = "";
        String[] split = newTime.split(":");
        //check array split empty or not
        if(split.length != 0){
            char[] cArray = split[0].toCharArray();
            if(cArray[0] == '0'){
                endTime = String.valueOf(cArray[1]) + "h";
            }else{
                endTime = split[0] + "h";
            }
            if(!split[1].equals("00")){
                endTime += split[1];
            }
        }
        return  endTime;
    }
    public long changeStringToTimeFormat(String string){
        return java.sql.Time.parse(string);
    }

}
