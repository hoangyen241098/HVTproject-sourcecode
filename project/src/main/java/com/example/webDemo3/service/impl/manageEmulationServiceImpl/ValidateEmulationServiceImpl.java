package com.example.webDemo3.service.impl.manageEmulationServiceImpl;

import com.example.webDemo3.entity.User;
import com.example.webDemo3.entity.ViolationClass;
import com.example.webDemo3.repository.ClassRedStarRepository;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.repository.ViolationClassRepository;
import com.example.webDemo3.service.manageEmulationService.ValidateEmulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/*
kimpt142 - 14/07
 */
@Service
public class ValidateEmulationServiceImpl implements ValidateEmulationService {

    @Autowired
    private ClassRedStarRepository classRedStarRepository;

    @Autowired
    private ViolationClassRepository violationClassRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * check role's user valid to emulate class
     * @param classID
     * @param username
     * @param dayEmulate
     * @return true if accepted, false if no action
     */
    @Override
    public Boolean checkRoleForEmulate(Integer classID, String username, Date dayEmulate) {

        Date biggestDateOfOther = classRedStarRepository.getBiggestClosetDateClassIdAndDifferRedStar(dayEmulate, classID,username);
        Date biggestDateOfUserName = classRedStarRepository.getBiggestClosetDateClassIdRedStar(dayEmulate, classID, username);

        if(biggestDateOfOther!=null) {
            if (biggestDateOfUserName == null || biggestDateOfOther.compareTo(biggestDateOfUserName) > 0) {
                return false;
            } else {
                return true;
            }
        }else{
            if(biggestDateOfUserName!=null){
                return true;
            }
            else{
                return false;
            }
        }
    }

    /**
     * check date is ranked or not
     * @param classId
     * @param date
     * @return yes if ranked, no if no ranked
     */
    @Override
    public Boolean checkRankedDate(Integer classId,Date date) {
        List<ViolationClass> rankedViolationList = violationClassRepository.findViolationClassRankedByClassId(classId, date);
        if(rankedViolationList !=null && rankedViolationList.size() > 0){
            return true;
        }
        return false;
    }

    /**
     * check violation is ranked or not
     * @param violationClassId
     * @return yes if ranked, no if no ranked
     */
    @Override
    public Boolean checkRankedDateByViolationId(Long violationClassId) {
        ViolationClass violationClass = violationClassRepository.findViolationClassByById(violationClassId);
        if(violationClass != null && violationClass.getWeekId() != 0){
            return true;
        }
        return false;
    }

    /**
     * get dayId by
     * @param date
     * @return
     */
    @Override
    public Integer getDayIdByDate(Date date) {
        LocalDate localDate = date.toLocalDate();
        DayOfWeek day = localDate.getDayOfWeek();
        return day.getValue();
    }

    /**
     * kimpt142
     * 14/07
     * @param classId
     * @param username
     * @return true if username is monitor of class and false is not
     */
    @Override
    public Boolean checkMonitorOfClass(Integer classId, String username) {
        User user = userRepository.findUserByUsername(username);
        if(user != null && user.getClassSchool().getClassId() == classId && (user.getStatus() == null || user.getStatus() !=1))
        {
            return true;
        }
        return false;
    }
}
