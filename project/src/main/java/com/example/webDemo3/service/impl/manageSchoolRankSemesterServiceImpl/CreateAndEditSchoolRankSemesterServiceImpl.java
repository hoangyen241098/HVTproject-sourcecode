package com.example.webDemo3.service.impl.manageSchoolRankSemesterServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListMonthSchoolRankResponseDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.SchoolMonthDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.SchoolWeekDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.CreateRankSemesterRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ListMonthSchoolRankRequestDto;
import com.example.webDemo3.entity.*;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.exception.MyException;
import com.example.webDemo3.repository.*;
import com.example.webDemo3.service.manageSchoolRankSemesterService.CreateAndEditSchoolRankSemester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateAndEditSchoolRankSemesterServiceImpl implements CreateAndEditSchoolRankSemester {

    @Autowired
    private SchoolYearRepository schoolYearRepository;

    @Autowired
    private SchoolMonthRepository schoolMonthRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchoolSemesterRepository schoolSemesterRepository;

    @Autowired
    private ClassRepository classRepository;

    @Override
    public ListMonthSchoolRankResponseDto loadListMonth(ListMonthSchoolRankRequestDto requestDto) {
        ListMonthSchoolRankResponseDto responseDto = new ListMonthSchoolRankResponseDto();
        List<SchoolMonthDto> monthListDto = new ArrayList<>();
        List<SchoolMonth> monthList =new ArrayList<>();
        MessageDTO message = new MessageDTO();

        Integer currentYearId = requestDto.getCurrentYearId();
        SchoolYear schoolYear = null;

        try{
            if(currentYearId == null){
                message = Constant.YEAR_ID_NULL;
                responseDto.setMessage(message);
                return  responseDto;
            }

            schoolYear = schoolYearRepository.findById(currentYearId).orElse(null);

            //check year exist or not
            if(schoolYear == null){
                message = Constant.YEAR_ID_NULL;
                responseDto.setMessage(message);
                return  responseDto;
            }

            monthList = schoolMonthRepository.findSchoolMonthNotRank(currentYearId);

            if(monthList == null){
                message = Constant.LIST_MONTH_NULL;
                responseDto.setMessage(message);
                return  responseDto;
            }

            for(SchoolMonth schoolMonth : monthList){
                SchoolMonthDto schoolMonthDto = new SchoolMonthDto();
                schoolMonthDto.setMonthId(schoolMonth.getMonthId());
                schoolMonthDto.setMonth(schoolMonth.getMonth());
                schoolMonthDto.setSemesterId(schoolMonth.getSemesterId());
                schoolMonthDto.setIsCheck(0);

                monthListDto.add(schoolMonthDto);
            }

            message = Constant.SUCCESS;
            responseDto.setMessage(message);
            responseDto.setMonthList(monthListDto);
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
    public MessageDTO createRankSemester(CreateRankSemesterRequestDto requestDto) {
        MessageDTO message = new MessageDTO();
        try {
            message = createRankSemesterTransaction(requestDto);
        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return message;
    }

    private MessageDTO createRankSemesterTransaction(CreateRankSemesterRequestDto requestDto) throws Exception{

        String userName = requestDto.getUserName();
        Integer semester = requestDto.getSemester();
        Integer currentYearId = requestDto.getCurrentYearId();
        List<SchoolMonthDto> monthList = requestDto.getMonthList();
        List<SchoolRankSemester> schoolRankSemesterList = new ArrayList<>();
        List<Class> classList = new ArrayList<>();

        MessageDTO message = new MessageDTO();
        User user = null;
        SchoolSemester schoolSemester = null;
        Integer semesterId = null;

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

            //check month empty or not
            if(semester == null){
                message = Constant.SEMESTER_NAME_EMPTY;
                return message;
            }

            //check currentYearId null or not
            if(currentYearId == null){
                message = Constant.YEAR_ID_NULL;
                return message;
            }

            //check dateList null or not
            if(monthList == null){
                message = Constant.MONTH_LIST_EMPTY;
                return message;
            }

            schoolSemester = schoolSemesterRepository.findSchoolSemesterBySemesterAndYearId(semester,currentYearId);
            //check month exist or not
            if(schoolSemester != null){
                message = Constant.SCHOOL_SEMESTER_EXISTS;
                return message;
            }

            schoolSemester.setYearId(currentYearId);
            schoolSemester.setSemester(semester);
            schoolSemesterRepository.save(schoolSemester);

            semesterId = schoolSemesterRepository.findSchoolSemesterBySemesterAndYearId(semester,currentYearId).getSemesterId();

            classList = classRepository.findAll();

            message = createOrEditSchoolRankSemester(classList,monthList,schoolRankSemesterList,semesterId,message);

        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            throw new MyException(message.getMessage());
        }
        return message;
    }

    private MessageDTO createOrEditSchoolRankSemester(List<Class> classList, List<SchoolMonthDto> monthList, List<SchoolRankSemester> schoolRankSemesterList, Integer semesterId, MessageDTO message){
        Integer size = 0;
        for(SchoolMonthDto schoolMonth : monthList){
            if(schoolMonth.getIsCheck() == 1 ){
                size ++;
            }
        }

        return message;
    }
}
