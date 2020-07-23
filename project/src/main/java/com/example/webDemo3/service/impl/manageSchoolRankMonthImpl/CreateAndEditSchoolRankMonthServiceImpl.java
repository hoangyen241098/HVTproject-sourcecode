package com.example.webDemo3.service.impl.manageSchoolRankMonthImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListWeekSchoolRankResponseDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.SchoolWeekDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.CreateRankMonthRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ListWeekSchoolRankRequestDto;
import com.example.webDemo3.entity.*;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.exception.MyException;
import com.example.webDemo3.repository.*;
import com.example.webDemo3.service.manageSchoolRankMonthService.CreateAndEditSchoolRankMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CreateAndEditSchoolRankMonthServiceImpl implements CreateAndEditSchoolRankMonthService {

    @Autowired
    private SchoolRankMonthRepository schoolRankMonthRepository;

    @Autowired
    private SchoolWeekRepository schoolWeekRepository;

    @Autowired
    private SchoolYearRepository schoolYearRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchoolMonthRepository schoolMonthRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private SchoolRankWeekRepository schoolRankWeekRepository;

    @Override
    public ListWeekSchoolRankResponseDto loadListWeek(ListWeekSchoolRankRequestDto requestDto) {
        ListWeekSchoolRankResponseDto responseDto = new ListWeekSchoolRankResponseDto();
        List<SchoolWeekDto> weekListDto = new ArrayList<>();
        List<SchoolWeek> weekList = new ArrayList<>();

        Integer currentYearId = requestDto.getCurrentYearId();
        SchoolYear schoolYear = null;
        MessageDTO message = new MessageDTO();

        try{
            if(currentYearId == null){
                message = Constant.YEAR_ID_NULL;
                responseDto.setMessage(message);
                return  responseDto;
            }

            schoolYear = schoolYearRepository.findById(currentYearId).orElse(null);

            if(schoolYear == null){
                message = Constant.YEAR_ID_NULL;
                responseDto.setMessage(message);
                return  responseDto;
            }

            weekList = schoolWeekRepository.findSchoolWeekNotRank(currentYearId);

            if(weekList == null){
                message = Constant.LIST_WEEK_NULL;
                responseDto.setMessage(message);
                return  responseDto;
            }

            for(SchoolWeek schoolWeek : weekList){
                SchoolWeekDto schoolWeekDto = new SchoolWeekDto();
                schoolWeekDto.setWeekId(schoolWeek.getWeekID());
                schoolWeekDto.setWeek(schoolWeek.getWeek());
                schoolWeekDto.setMonthId(schoolWeek.getMonthID());
                schoolWeekDto.setIsCheck(0);

                weekListDto.add(schoolWeekDto);
            }

            message = Constant.SUCCESS;
            responseDto.setMessage(message);
            responseDto.setWeekList(weekListDto);
        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            responseDto.setMessage(message);
            return responseDto;
        }

        return  responseDto;
    }

    @Override
    @Transactional
    public MessageDTO createRankMonth(CreateRankMonthRequestDto requestDto) {
        MessageDTO message = new MessageDTO();
        try {
            message = createRankMonthTransaction(requestDto);
        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return message;
    }

    private MessageDTO createRankMonthTransaction(CreateRankMonthRequestDto requestDto) throws Exception{
        MessageDTO message = new MessageDTO();

        String userName = requestDto.getUserName();
        Integer month = requestDto.getMonth();
        Integer currentYearId = requestDto.getCurrentYearId();
        List<SchoolWeekDto> weekList = requestDto.getWeekList();
        List<SchoolRankMonth> schoolRankMonthList = new ArrayList<>();

        User user;
        SchoolMonth schoolMonth;
        Integer monthId;
        List<Class> classList = new ArrayList<>();
        try{
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
            if(month == null){
                message = Constant.MONTH_NAME_EMPTY;
                return message;
            }

            //check currentYearId null or not
            if(currentYearId == null){
                message = Constant.YEAR_ID_NULL;
                return message;
            }

            //check dateList null or not
            if(weekList == null){
                message = Constant.WEEK_LIST_EMPTY;
                return message;
            }

            schoolMonth = schoolMonthRepository.findSchoolMonthByMonthAndSemesterAndYearId(month,0,currentYearId);
            //check week exist or not
            if(schoolMonth != null){
                message = Constant.SCHOOL_WEEK_EXISTS;
                return message;
            }

            schoolMonth = new SchoolMonth();
            schoolMonth.setMonth(month);
            schoolMonth.setSemesterId(0);
            schoolMonth.setYearId(currentYearId);
            schoolMonthRepository.save(schoolMonth);

            monthId = schoolMonthRepository.findSchoolMonthByMonthAndSemesterAndYearId(month,0,currentYearId).getMonthId();

            classList = classRepository.findAll();

            message = createOrEditSchoolRankMonth(classList,weekList,schoolRankMonthList,monthId,message);
        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            throw new MyException(message.getMessage());
        }
        return message;
    }

    private MessageDTO createOrEditSchoolRankMonth(List<Class> classList,List<SchoolWeekDto> weekList,List<SchoolRankMonth> schoolRankMonthList, Integer monthId, MessageDTO message) throws Exception{
        Integer size = 0;
        boolean check = false;
        for(SchoolWeekDto schoolWeekDto : weekList){
            if(schoolWeekDto.getIsCheck() == 1){
                size ++;
            }
        }

        for(Class newClass : classList){
            Double totalGrade = 0.0;
            Integer totalRank = 0;

            for(SchoolWeekDto schoolWeekDto : weekList){
                Integer isCheck = schoolWeekDto.getIsCheck();
                if(isCheck == 1){
                    check = true;
                    SchoolWeek schoolWeek = schoolWeekRepository.findSchoolWeekByWeekID(schoolWeekDto.getWeekId());
                    if(schoolWeek != null){
                        schoolWeek.setMonthID(monthId);
                        schoolWeekRepository.save(schoolWeek);
                        SchoolRankWeek schoolRankWeek = schoolRankWeekRepository.findSchoolRankWeekByWeekIdAndClassId(schoolWeek.getWeekID(),newClass.getClassId());

                        if(schoolRankWeek != null){
                            totalGrade += schoolRankWeek.getTotalGrade();
                            totalRank += schoolRankWeek.getRank();
                        }
                    }
                }
            }
            if(check){
                SchoolRankMonth schoolRankMonth = new SchoolRankMonth();

                SchoolRankMonthId schoolRankMonthId = new SchoolRankMonthId();
                schoolRankMonthId.setMONTH_ID(monthId);
                schoolRankMonthId.setSchoolClass(new Class(newClass.getClassId()));

                schoolRankMonth.setSchoolRankMonthId(schoolRankMonthId);
                schoolRankMonth.setTotalGradeWeek(round(totalGrade));
                schoolRankMonth.setTotalRankWeek(totalRank);

                schoolRankMonthList.add(schoolRankMonth);
            }
        }

        for(SchoolRankMonth schoolRankMonth : schoolRankMonthList){
            Class newClass = classRepository.findById(schoolRankMonth.getSchoolRankMonthId().getSchoolClass().getClassId()).orElse(null);
            if(newClass != null){
                classList.remove(newClass);
            }
        }

        //check schoolRankWeekList null or not
        if((schoolRankMonthList == null || schoolRankMonthList.size() == 0)){
            message = Constant.SCHOOL_RANK_WEEK_NULL;
            throw new MyException(message.getMessage());
        }
        List<SchoolRankMonth> newSchoolRankMonthList1 = arrangeSchoolRankMonth(schoolRankMonthList);

        for(int i = 0; i < newSchoolRankMonthList1.size(); i++){
            SchoolRankMonth schoolRankMonth = newSchoolRankMonthList1.get(i);
            schoolRankMonthRepository.save(schoolRankMonth);
        }

        message = Constant.SUCCESS;
        return message;
    }

    private List<SchoolRankMonth> arrangeSchoolRankMonth(List<SchoolRankMonth> schoolRankMonthList) {
        Collections.sort(schoolRankMonthList, new Comparator<SchoolRankMonth>() {
            @Override
            public int compare(SchoolRankMonth o1, SchoolRankMonth o2) {
                return o2.getTotalRankWeek().compareTo(o1.getTotalRankWeek());
            }
        });
        int rank = 1;
        if(schoolRankMonthList.size() == 1){
            schoolRankMonthList.get(0).setRank(rank);
            return schoolRankMonthList;
        }
        else {
            int count = 0;
            for (int i = 0; i < schoolRankMonthList.size() - 1; i++) {
                SchoolRankMonth schoolRankMonth = schoolRankMonthList.get(i);
                schoolRankMonthList.get(i).setRank(rank);
                SchoolRankMonth schoolRankMonth2 = schoolRankMonthList.get(i + 1);
                if (schoolRankMonth2.getTotalRankWeek().compareTo(schoolRankMonth.getTotalRankWeek()) == 0) {
                    schoolRankMonthList.get(i + 1).setRank(rank);
                    count++;
                } else {
                    rank += count + 1;
                    count = 0;
                    schoolRankMonthList.get(i + 1).setRank(rank);
                }
            }
        }
        return schoolRankMonthList;
    }

    private double round(Double input) {
        return (double) Math.round(input * 100) / 100;
    }
}