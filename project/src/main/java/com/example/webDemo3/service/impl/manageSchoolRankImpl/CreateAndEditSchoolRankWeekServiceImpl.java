package com.example.webDemo3.service.impl.manageSchoolRankImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.DateViolationClassDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListDateResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.CreateRankWeekRequestDto;
import com.example.webDemo3.entity.*;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.repository.*;
import com.example.webDemo3.service.manageEmulationService.ValidateEmulationService;
import com.example.webDemo3.service.manageSchoolRank.CreateAndEditSchoolRankWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * lamnt98
 * 21/07
 */

@Service
public class CreateAndEditSchoolRankWeekServiceImpl implements CreateAndEditSchoolRankWeekService {

    @Autowired
    private SchoolRankWeekRepository schoolRankWeekRepository;

    @Autowired
    private ViolationClassRepository violationClassRepository;

    @Autowired
    private ValidateEmulationService validateEmulationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private SchoolWeekRepository schoolWeekRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ViolationTypeRepository violationTypeRepository;

    @Override
    public ListDateResponseDto loadListDate() {
        ListDateResponseDto responseDto = new ListDateResponseDto();
        List<DateViolationClassDto> dateResponseList = new ArrayList<>();
        List<Date> dateList = new ArrayList<>();
        MessageDTO message = new MessageDTO();

        Date biggestDate = null;

        try{
            biggestDate = violationClassRepository.findBiggestDateRanked();
            dateList = violationClassRepository.findListDateByCondition(biggestDate);

            //check dateList empty or not
            if(dateList.size() == 0){
                message = Constant.DATE_LIST_EMPTY;
                responseDto.setMessage(message);
                return responseDto;
            }

            for(int i = 0; i < dateList.size(); i++){
                Date date = dateList.get(i);
                String dayName = getDayNameByDate(date);

                DateViolationClassDto dateResponseDto = new DateViolationClassDto();

                dateResponseDto.setDate(date);
                dateResponseDto.setDayName(dayName);
                dateResponseDto.setIsCheck(0);

                dateResponseList.add(dateResponseDto);
            }

            message = Constant.SUCCESS;
            responseDto.setDateList(dateResponseList);
            responseDto.setMessage(message);
        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            responseDto.setMessage(message);
            return responseDto;
        }

        return responseDto;
    }

    @Override
    public MessageDTO createRankWeek(CreateRankWeekRequestDto requestDto) {
        MessageDTO message = new MessageDTO();
        String userName = requestDto.getUserName();
        Integer week = requestDto.getWeek();
        Integer currentYearId = requestDto.getCurrentYearId();
        List<DateViolationClassDto> dateList = requestDto.getDateList();
        List<Class> classList = new ArrayList<>();
        SchoolWeek schoolWeek;
        List<SchoolRankWeek> schoolRankWeekList = new ArrayList<>();
        Integer weekId;
        Float allTotalGrade;
        Integer monthId = 0;
        User user;

        try {


            //check userName empty or not
            if(userName.isEmpty()){
                message = Constant.USERNAME_EMPTY;
                return message;
            }

            user = userRepository.findUserByUsername(userName);
            //check user null or not
            if(user == null){
                message = Constant.USER_NOT_EXIT;
                return message;
            }

            //check user have permisson or not
            if(user.getRole().getRoleId() != Constant.ROLEID_ADMIN){
                message = Constant.NOT_ACCEPT_CREATE_RANK_WEEK;
                return message;
            }

            //check userName empty or not
            if(week == null){
                message = Constant.WEEK_NAME_EMPTY;
                return message;
            }

            //check currentYearId null or not
            if(currentYearId == null){
                message = Constant.YEAR_ID_NULL;
                return message;
            }

            //check dateList null or not
            if(dateList == null){
                message = Constant.DATE_LIST_EMPTY;
                return message;
            }

            schoolWeek = schoolWeekRepository.findSchoolWeeksByWeekMonthIdAndYearId(week,0,currentYearId);
            //check week exist or not
            if(schoolWeek != null){
                message = Constant.SCHOOL_WEEK_EXISTS;
                return message;
            }

            schoolWeek = new SchoolWeek();
            schoolWeek.setWeek(week);
            schoolWeek.setMonthID(monthId);
            schoolWeek.setYearId(currentYearId);
            schoolWeekRepository.save(schoolWeek);

            weekId = schoolWeekRepository.findSchoolWeeksByWeekMonthIdAndYearId(week,0,currentYearId).getWeekID();

            classList = classRepository.findAll();
            allTotalGrade = violationTypeRepository.sumAllTotalGradeViolationTypeActive();

            message = createOrEditSchoolRankWeek(classList, dateList,allTotalGrade, schoolRankWeekList,weekId,message);

        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }
        return message;
    }

    public String getDayNameByDate(Date date) {
        LocalDate localDate = date.toLocalDate();
        DayOfWeek day = localDate.getDayOfWeek();
        Integer dayId = day.getValue();
        String dayName = dayRepository.findByDayId(dayId).getDayName();
        return dayName;
    }

    public MessageDTO createOrEditSchoolRankWeek(List<Class> classList, List<DateViolationClassDto> dateList,Float allTotalGrade, List<SchoolRankWeek> schoolRankWeekList,Integer weekId, MessageDTO message){
        List<ViolationClass> violationClassList = new ArrayList<>();
        for(Class newClass: classList){
            Float allScore = (float) 0;
            Integer size = 0;
            for(DateViolationClassDto date: dateList){
                Integer isCheck = date.getIsCheck();

                //check isCheck = 1 or not
                if(isCheck == 1){
                    Float totalSubTractGrade = (float) 0;
                    violationClassList = violationClassRepository.findByDateClassAndStatus(date.getDate(),newClass.getClassId(),1);
                    size = violationClassList.size();

                    //check size = 0 or not
                    if(size == 0){
                        break;
                    }

                    for(ViolationClass violationClass: violationClassList){
                        violationClass.setWeekId(weekId);
                        totalSubTractGrade += violationClass.getQuantity() * violationClass.getViolation().getSubstractGrade();
                        allScore += allTotalGrade - totalSubTractGrade;
                        violationClassRepository.save(violationClass);
                    }
                }
            }
            //check size = 0 or not
            if(size != 0){
                Float EMULATION_GRADE = allScore / size;
                Float TOTAL_GRADE = EMULATION_GRADE + Constant.LEARNING_GRADE + Constant.MOVEMENT_GRADE + Constant.LABOR_GRADE;

                SchoolRankWeekId schoolRankWeekId = new SchoolRankWeekId();
                schoolRankWeekId.setSchoolClass(new Class(newClass.getClassId()));
                schoolRankWeekId.setWEEK_ID(weekId);

                SchoolRankWeek schoolRankWeek = new SchoolRankWeek();
                schoolRankWeek.setSchoolRankWeekId(schoolRankWeekId);
                schoolRankWeek.setEmulationGrade(EMULATION_GRADE);
                schoolRankWeek.setLaborGrade(Constant.LABOR_GRADE);
                schoolRankWeek.setLearningGrade(Constant.LEARNING_GRADE);
                schoolRankWeek.setMovementGrade(Constant.MOVEMENT_GRADE);
                schoolRankWeek.setTotalGrade(TOTAL_GRADE);

                schoolRankWeekList.add(schoolRankWeek);
            }
        }
        //check schoolRankWeekList null or not
        if(schoolRankWeekList == null || schoolRankWeekList.size() == 0){
            message = Constant.SCHOOL_RANK_WEEK_NULL;
            return message;
        }
        List<SchoolRankWeek> newSchoolRankWeekList = arrangeRank(schoolRankWeekList);

        for(SchoolRankWeek schoolRankWeek : newSchoolRankWeekList){
            schoolRankWeekRepository.save(schoolRankWeek);
        }
        message = Constant.SUCCESS;
        return message;
    }

    private List<SchoolRankWeek> arrangeRank(List<SchoolRankWeek> schoolRankWeekList){
        Collections.sort(schoolRankWeekList, new Comparator<SchoolRankWeek>() {
            @Override
            public int compare(SchoolRankWeek o1, SchoolRankWeek o2) {
                return o2.getTotalGrade().compareTo(o1.getTotalGrade());
            }
        });
        int rank = 1;
        //check size of schoolRankWeekList = 1 or not
        if(schoolRankWeekList.size() == 1){
            schoolRankWeekList.get(0).setRank(rank);
            return schoolRankWeekList;
        }
        else {
            int count = 0;
            for (int i = 0; i < schoolRankWeekList.size() - 1; i++) {
                SchoolRankWeek schoolRankWeek1 = schoolRankWeekList.get(i);
                schoolRankWeekList.get(i).setRank(rank);
                SchoolRankWeek schoolRankWeek2 = schoolRankWeekList.get(i + 1);
                //check two totalGrade of two schoolWeek equal or not
                if (schoolRankWeek2.getTotalGrade().compareTo(schoolRankWeek1.getTotalGrade()) == 0) {
                    schoolRankWeekList.get(i + 1).setRank(rank);
                    count++;
                } else {
                    rank += count + 1;
                    schoolRankWeekList.get(i + 1).setRank(rank);
                }
            }
        }
        return schoolRankWeekList;
    }
}
