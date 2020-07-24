package com.example.webDemo3.service.impl.manageSchoolRankSemesterServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.*;
import com.example.webDemo3.dto.manageSchoolYearResponseDto.SchoolYearTableResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.LoadByYearIdRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.SearchRankSemesterRequestDto;
import com.example.webDemo3.entity.SchoolRankSemester;
import com.example.webDemo3.entity.SchoolSemester;
import com.example.webDemo3.repository.SchoolRankSemesterRepository;
import com.example.webDemo3.repository.SchoolSemesterRepository;
import com.example.webDemo3.service.manageSchoolRankSemesterService.ViewSchoolRankSemesterService;
import com.example.webDemo3.service.manageSchoolYearService.SchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/*
kimpt142 - 24/07
 */
@Service
public class ViewSchoolRankSemesterServiceImpl implements ViewSchoolRankSemesterService {

    @Autowired
    private SchoolSemesterRepository schoolSemesterRepository;

    @Autowired
    private SchoolRankSemesterRepository schoolRankSemesterRepository;

    @Autowired
    private SchoolYearService schoolYearService;

    /**
     * kimpt142
     * 24/07
     * get year list and semester list by yearid
     * @param model include yearid
     * @return schoolyearlist, semesterlist and message
     */
    @Override
    public LoadRankSemesterResponseDto loadRankSemesterPage(LoadByYearIdRequestDto model) {
        LoadRankSemesterResponseDto responseDto = new LoadRankSemesterResponseDto();
        MessageDTO message;

        //get year list
        SchoolYearTableResponseDto schoolYearListDto = schoolYearService.getSchoolYearTable();
        if(schoolYearListDto.getMessage().getMessageCode() == 1){
            message = schoolYearListDto.getMessage();
            responseDto.setMessage(message);
            return responseDto;
        }
        else{
            responseDto.setSchoolYearList(schoolYearListDto.getSchoolYearList());
        }

        ViewSemesterListResponseDto schoolSemesterListDto = getSemesterListByYearId(model);
        if(schoolSemesterListDto.getMessage().getMessageCode() == 1){
            message = schoolSemesterListDto.getMessage();
            responseDto.setMessage(message);
            return responseDto;
        }else{
            responseDto.setSchoolSemesterList(schoolSemesterListDto.getSchoolSemesterList());
        }

        message = Constant.SUCCESS;
        responseDto.setMessage(message);
        return responseDto;
    }

    /**
     * kimpt142
     * 24/07
     * get semster list by year id and exclude item with semester id = 0
     * @param model include yearid
     * @return a semester list and message
     */
    @Override
    public ViewSemesterListResponseDto getSemesterListByYearId(LoadByYearIdRequestDto model) {
        ViewSemesterListResponseDto responseDto = new ViewSemesterListResponseDto();
        MessageDTO message;

        Integer yearId = model.getYearId();
        if(yearId == null){
            message = Constant.SCHOOLYEARID_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
        }

        List<SchoolSemester> schoolSemesterList = schoolSemesterRepository.findSchoolSemesterByYearIdExcludeZero(yearId);
        if(schoolSemesterList == null || schoolSemesterList.size() == 0){
            message = Constant.SEMESTERLIST_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
        }else{
            responseDto.setSchoolSemesterList(schoolSemesterList);
        }

        message = Constant.SUCCESS;
        responseDto.setMessage(message);
        return responseDto;
    }

    /**
     * kimpt142
     * 24/07
     * get rank semester list by month id
     * @param model
     * @return
     */
    @Override
    public RankSemesterListResponseDto searchRankSemesterById(SearchRankSemesterRequestDto model) {
        RankSemesterListResponseDto resposeDto = new RankSemesterListResponseDto();
        List<RankSemesterResponseDto> rankSemesterList = new ArrayList<>();
        MessageDTO message;

        Integer semesterId = model.getSemesterId();
        if(semesterId == null){
            message = Constant.SEMESTERID_EMPTY;
            resposeDto.setMessage(message);
            return resposeDto;
        }

        List<SchoolRankSemester> schoolRankSemesterList = schoolRankSemesterRepository.findAllBySchoolRankSemesterId(semesterId);
        if(schoolRankSemesterList == null || schoolRankSemesterList.size() == 0)
        {
            message = Constant.RANKMONTHLIST_EMPTY;
            resposeDto.setMessage(message);
            return resposeDto;
        }
        else{
            for(SchoolRankSemester item : schoolRankSemesterList) {
                RankSemesterResponseDto rankSemesterDto = new RankSemesterResponseDto();
                rankSemesterDto.setClassId(item.getSchoolRankSemesterId().getSchoolClass().getClassId());
                rankSemesterDto.setClassName(item.getSchoolRankSemesterId().getSchoolClass().getGrade()+ " "
                        + item.getSchoolRankSemesterId().getSchoolClass().getGiftedClass().getName());
                rankSemesterDto.setTotalGradeMonth(round(item.getTotalGradeMonth()));
                rankSemesterDto.setTotalRankMonth(item.getTotalRankMonth());
                rankSemesterDto.setRank(item.getRank());
                rankSemesterDto.setHistory(item.getHistory());
                rankSemesterList.add(rankSemesterDto);
            }
        }

        message = Constant.SUCCESS;
        resposeDto.setMessage(message);
        resposeDto.setRankSemesterList(rankSemesterList);
        return resposeDto;
    }

    private double round(Double input) {
        return (double) Math.round(input * 100) / 100;
    }
}
