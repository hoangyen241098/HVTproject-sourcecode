package com.example.webDemo3.service.impl.manageEmulationServiceImpl;

import com.example.webDemo3.repository.ClassRedStarRepository;
import com.example.webDemo3.service.manageEmulationService.ValidateEmulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

/*
kimpt142 - 14/07
 */
@Service
public class ValidateEmulationServiceImpl implements ValidateEmulationService {

    @Autowired
    private ClassRedStarRepository classRedStarRepository;

    @Override
    public Boolean checkRoleForEmulate(Integer classID, String username, Date dayEmulate) {

        Date biggestDateOfOther = classRedStarRepository.getBiggestClosetDateClassIdAndDifferRedStar(dayEmulate, classID,username);
        Date biggestDateOfUserName = classRedStarRepository.getBiggestClosetDateClassIdRedStar(dayEmulate, classID, username);
        if(biggestDateOfOther.compareTo(biggestDateOfUserName) > 0 ){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public Boolean checkRankedDate(Date date) {
        return null;
    }
}
