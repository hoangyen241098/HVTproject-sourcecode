package com.example.webDemo3.service.impl.manageEmulationServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageClassResponseDto.ClassListResponseDto;
import com.example.webDemo3.dto.manageClassResponseDto.ClassResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewGradingEmulationResponseDto;
import com.example.webDemo3.dto.manageViolationResponseDto.ViolationTypeResponseDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.AddViolationForClassRequestDto;
import com.example.webDemo3.dto.request.manageEmulationRequestDto.SubViolationForClassRequestDto;
import com.example.webDemo3.entity.SchoolYear;
import com.example.webDemo3.repository.SchoolYearRepository;
import com.example.webDemo3.service.manageClassService.ClassService;
import com.example.webDemo3.service.manageEmulationService.GradingEmulationService;
import com.example.webDemo3.service.manageEmulationService.ValidateEmulationService;
import com.example.webDemo3.service.manageViolationService.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/*
kimpt142 - 14/07
 */
@Service
public class GradingEmulationServiceImpl implements GradingEmulationService {

    @Autowired
    private ClassService classService;

    @Autowired
    private ViolationService violationService;

    @Autowired
    SchoolYearRepository schoolYearRepository;

    @Autowired
    private ValidateEmulationService validateEmulationService;

    /**
     * kimpt142
     * 14/07
     * get class list and violation list to show in grading emulation
     * @return a class list, a list include violation type and violation and message
     */
    @Override
    public ViewGradingEmulationResponseDto getClassAndViolationList() {
        ViewGradingEmulationResponseDto responseDto = new ViewGradingEmulationResponseDto();
        MessageDTO message = new MessageDTO();

        List<ClassResponseDto> classList = classService.getClassList().getClassList();
        List<ViolationTypeResponseDto> vioTypeAndVioList = violationService.getListViolationAndType().getListViolationType();

        if(classList == null){
            message = Constant.CLASSLIST_NOT_EXIT;
            responseDto.setMessage(message);
            return responseDto;
        }

        if(vioTypeAndVioList == null){
            message = Constant.VIOLATION_TYPE_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
        }

        responseDto.setClassList(classList);
        responseDto.setVioTypeAndVioList(vioTypeAndVioList);
        responseDto.setMessage(Constant.SUCCESS);
        return responseDto;
    }

    /**
     * kimpt142
     * 14/07
     * add new grade emulation for class (insert into violation class)
     * @param model include information class, violation and date
     * @return message
     */
    @Override
    public MessageDTO addViolationForClass(AddViolationForClassRequestDto model) {

        MessageDTO message = new MessageDTO();
        String username = model.getUsername();
        Integer classId = model.getClassId();
        Date date = model.getDate();
        Integer yearId = model.getYearId();
        List<SubViolationForClassRequestDto> violationList = model.getViolationList();

        if(classId == null){
            message = Constant.CLASS_EMPTY;
            return message;
        }

        if(date == null){
            message = Constant.DATE_EMPTY;
            return message;
        }

        if(yearId == null){
            Date dateCurrent = new Date(System.currentTimeMillis());
            SchoolYear schoolCurrent = schoolYearRepository.findSchoolYearsByDate(dateCurrent);
            if(schoolCurrent != null) {
                yearId = schoolCurrent.getYearID();
            }
        }

        if(violationList == null || violationList.size() == 0){
            message = Constant.VIOLATION_ID_NULL;
            return message;
        }

        if(validateEmulationService.checkRoleForEmulate(classId, ))


        return null;
    }
}
