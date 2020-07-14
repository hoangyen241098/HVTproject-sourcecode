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
    public ViewRequestResponseDto viewRequest(ViewRequestDto viewRequest) {
        ViewRequestResponseDto viewRequestResponseDto = new ViewRequestResponseDto();
        return viewRequestResponseDto;
    }


}
