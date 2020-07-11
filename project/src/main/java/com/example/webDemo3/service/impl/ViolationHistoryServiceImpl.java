package com.example.webDemo3.service.impl;

import com.example.webDemo3.dto.request.ViolationHistoryResquestDTO;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.Violation;
import com.example.webDemo3.entity.ViolationClass;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.repository.ViolationClassRepository;
import com.example.webDemo3.service.ViolationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViolationHistoryServiceImpl implements ViolationHistoryService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ViolationClassRepository violationClassRepository;

    @Override
    public List<ViolationClass> getHistoryViolationOfClas(ViolationHistoryResquestDTO model) {
        List<ViolationClass> violationClassList = new ArrayList<>();
        List<Class> classList = classRepository.findByGifted(model.getGiftedId());
        for (int i = 0; i < classList.size(); i++) {
            int classID = classList.get(i).getClassId();
        }
        violationClassList = violationClassRepository.findAll();
        return violationClassList;
    }

}
