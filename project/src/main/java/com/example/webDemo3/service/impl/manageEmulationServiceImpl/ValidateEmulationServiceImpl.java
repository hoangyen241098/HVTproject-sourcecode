package com.example.webDemo3.service.impl.manageEmulationServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.entity.ViolationClass;
import com.example.webDemo3.entity.ViolationEnteringTime;
import com.example.webDemo3.repository.ClassRedStarRepository;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.repository.ViolationClassRepository;
import com.example.webDemo3.repository.ViolationEnteringTimeRepository;
import com.example.webDemo3.service.manageEmulationService.ValidateEmulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
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

    @Autowired
    private ViolationEnteringTimeRepository violationEnteringTimeRepository;

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
        if(user != null && user.getClassSchool() != null && user.getClassSchool().getClassId() == classId
                && user.getRole() != null && user.getRole().getRoleId() == 4
                && (user.getStatus() == null || user.getStatus() !=1))
        {
            return true;
        }
        return false;
    }

    /**
     * kimpt142
     * 19/07
     * check user can add violation of class
     * @param username
     * @param roleId
     * @param classId
     * @param addDate
     * @return true if user can add
     */
    @Override
    public Boolean checkRoleForAddViolationClass(String username, Integer roleId,Integer classId , Date addDate) {
        Time time = new Time(System.currentTimeMillis());

        //admin can add into violation class
        if(roleId == 1 && checkImportViolationClass(addDate)){
            return true;
        }
        //summerize group can add into violation class
        else if (roleId == 5 && checkImportViolationWithEnteringTime(roleId, addDate, time)
                && checkImportViolationClass(addDate)){
            return true;
        }
        //red star can add into violation class
        else if(roleId == 3 && checkRoleForEmulate(classId, username, addDate)
                && checkImportViolationWithEnteringTime(roleId,addDate, time)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * kimpt142
     * 19/07
     * check user can edit the violation of class
     * @param username
     * @param roleId
     * @param classId
     * @param date
     * @return true if user can edit
     */
    @Override
    public Boolean checkRoleForEditViolationClass(String username, Integer roleId,Integer classId , Date date) {
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Time time = new Time(System.currentTimeMillis());

        //admin can edit violation of class
        if(roleId == 1 && checkImportViolationClass(date)){
            return true;
        }
        //check monitor can edit violation of class
        else if (checkMonitorOfClass(classId, username)
                && sdf.format(currentDate).equalsIgnoreCase(sdf.format(date))){
            return true;
        }
        //red star can edit violation of class
        else if(roleId == 3 && checkRoleForEmulate(classId, username, date)
                && checkImportViolationWithEnteringTime(roleId,date, time)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * kimpt142 - 19/07
     * check user can do with violation of class when the date <= current date and this date is greater than the biggest ranked date
     * @param date
     * @return
     */
    private Boolean checkImportViolationClass(Date date) {
        Date rankedDate = violationClassRepository.findBiggestDateRanked(date);
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(rankedDate == null ||((sdf.format(currentDate).equalsIgnoreCase(sdf.format(date)) || date.compareTo(currentDate) < 0)
                && date.compareTo(rankedDate) > 0 )){
            return true;
        }
        return false;
    }

    /**
     * kimpt142
     * 19/07
     * check user can enter violation of specific time in a day (Violation_entering_time table).
     * @param roleId
     * @param date
     * @param time
     * @return
     */
    private Boolean checkImportViolationWithEnteringTime(Integer roleId, Date date, Time time) {
        Integer dayId = getDayIdByDate(date);
        List<ViolationEnteringTime> violationEnteringTimeList = violationEnteringTimeRepository.findEnteringTimeRoleIdAndDayId(roleId,dayId,time);
        if(violationEnteringTimeList != null && violationEnteringTimeList.size() != 0)
        {
            return true;
        }
        return false;
    }
}
