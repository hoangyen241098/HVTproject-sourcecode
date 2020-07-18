package com.example.webDemo3.service.impl.assignRedStarServiceImpl;

import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.ClassRedStar;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.ClassRedStarRepository;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateAssignRedStarServiceImpl {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRedStarRepository classRedStarRepository;

    public void create(Date fromDate){
        List<Class> classList = classRepository.findAll();
        List<User> RedStarList = userRepository.finRedStar();
                //finRedStar();
        Date beforDate = classRedStarRepository.getBiggestClosetDate(fromDate);
        List<ClassRedStar> assignList = new ArrayList<>();
        if(beforDate != null){
            assignList = classRedStarRepository.findAllByDate(beforDate);
        }
        int k=1;
    }

}
