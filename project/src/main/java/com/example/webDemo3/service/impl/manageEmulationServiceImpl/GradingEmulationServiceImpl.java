package com.example.webDemo3.service.impl.manageEmulationServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageClassResponseDto.ClassListResponseDto;
import com.example.webDemo3.dto.manageClassResponseDto.ClassResponseDto;
import com.example.webDemo3.dto.manageEmulationResponseDto.ViewGradingEmulationResponseDto;
import com.example.webDemo3.dto.manageViolationResponseDto.ViolationTypeResponseDto;
import com.example.webDemo3.service.manageClassService.ClassService;
import com.example.webDemo3.service.manageEmulationService.GradingEmulationService;
import com.example.webDemo3.service.manageViolationService.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
