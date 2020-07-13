package com.example.webDemo3.service.impl.manageEmulationServiceImpl;

import com.example.webDemo3.dto.request.manageEmulationRequestDto.ViolationHistoryResquestDTO;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.ViolationClass;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.repository.ViolationClassRepository;
import com.example.webDemo3.service.manageEmulationService.ViolationHistoryService;
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
            Class classSchool = classList.get(i);
            if (classSchool == null) break;
            int fromYear = model.getFromYear();;
            if(classSchool.getGrade() == 11){
                fromYear = model.getFromYear()+1;
            }
            else if(classSchool.getGrade() == 12){
                fromYear = model.getFromYear()+2;
            }
            List<ViolationClass> subList =violationClassRepository.findHistoryOfClass(classSchool.getClassId(),fromYear,
                    model.getFromDate(),model.getToDate());
            violationClassList.addAll(subList);
        }
        //violationClassList = violationClassRepository.findAll();
        return violationClassList;
    }

}
