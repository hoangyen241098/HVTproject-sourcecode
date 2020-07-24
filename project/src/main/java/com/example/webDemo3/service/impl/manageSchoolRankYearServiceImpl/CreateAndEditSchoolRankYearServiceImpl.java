package com.example.webDemo3.service.impl.manageSchoolRankYearServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListSemesterSchoolRankResponseDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.SchoolSemesterDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ViewSemesterOfEditRankYearRequestDto;
import com.example.webDemo3.entity.SchoolSemester;
import com.example.webDemo3.entity.SchoolYear;
import com.example.webDemo3.repository.SchoolSemesterRepository;
import com.example.webDemo3.repository.SchoolYearRepository;
import com.example.webDemo3.service.manageSchoolRankYearSerivce.CreateAndEditSchoolRankYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateAndEditSchoolRankYearServiceImpl implements CreateAndEditSchoolRankYearService {

    @Autowired
    private SchoolSemesterRepository schoolSemesterRepository;

    @Autowired
    private SchoolYearRepository schoolYearRepository;

    @Override
    public ListSemesterSchoolRankResponseDto loadListSemester() {
        ListSemesterSchoolRankResponseDto responseDto = new ListSemesterSchoolRankResponseDto();
        List<SchoolSemesterDto> semesterListDto = new ArrayList<>();
        List<SchoolSemester> semesterList =new ArrayList<>();
        MessageDTO message = new MessageDTO();


        try{

            semesterList = schoolSemesterRepository.findSchoolSemesterNotRank();

            if(semesterList == null || semesterList.size() == 0){
                message = Constant.SEMESTER_LIST_EMPTY;
                responseDto.setMessage(message);
                return  responseDto;
            }

            for(SchoolSemester schoolSemester : semesterList){
                SchoolSemesterDto schoolSemesterDto = new SchoolSemesterDto();
                schoolSemesterDto.setSemesterId(schoolSemester.getSemesterId());
                schoolSemesterDto.setSemester(schoolSemester.getSemester());
                schoolSemesterDto.setYearId(schoolSemester.getYearId());
                schoolSemesterDto.setIsCheck(0);

                semesterListDto.add(schoolSemesterDto);
            }

            message = Constant.SUCCESS;
            responseDto.setMessage(message);
            responseDto.setSemesterList(semesterListDto);
        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            responseDto.setMessage(message);
            return responseDto;
        }
        return  responseDto;
    }

    @Override
    public ListSemesterSchoolRankResponseDto loadEditListSemester(ViewSemesterOfEditRankYearRequestDto requestDto) {
        ListSemesterSchoolRankResponseDto responseDto = new ListSemesterSchoolRankResponseDto();
        List<SchoolSemesterDto> semesterListDto = new ArrayList<>();
        List<SchoolSemester> semesterList = new ArrayList<>();
        List<SchoolSemester> newSemesterList = new ArrayList<>();
        MessageDTO message = new MessageDTO();

        Integer yearId = requestDto.getYearId();
        SchoolYear schoolYear;

        try {
            if (yearId == null) {
                message = Constant.YEAR_ID_NULL;
                responseDto.setMessage(message);
                return responseDto;
            }

            schoolYear = schoolYearRepository.findById(yearId).orElse(null);

            //check year exist or not
            if (schoolYear == null) {
                message = Constant.YEAR_ID_NULL;
                responseDto.setMessage(message);
                return responseDto;
            }

            semesterList = schoolSemesterRepository.findSchoolSemesterNotRank();

            for (SchoolSemester schoolSemester : semesterList) {
                SchoolSemesterDto schoolSemesterDto = new SchoolSemesterDto();
                schoolSemesterDto.setSemesterId(schoolSemester.getSemesterId());
                schoolSemesterDto.setSemester(schoolSemester.getSemester());
                schoolSemesterDto.setYearId(schoolSemester.getYearId());
                schoolSemesterDto.setIsCheck(0);

                semesterListDto.add(schoolSemesterDto);
            }

            newSemesterList = schoolSemesterRepository.findSchoolSemesterByYearId(yearId);

            for (SchoolSemester schoolSemester : newSemesterList) {
                SchoolSemesterDto schoolSemesterDto = new SchoolSemesterDto();
                schoolSemesterDto.setSemesterId(schoolSemester.getSemesterId());
                schoolSemesterDto.setSemester(schoolSemester.getSemester());
                schoolSemesterDto.setYearId(schoolSemester.getYearId());
                schoolSemesterDto.setIsCheck(1);

                semesterListDto.add(schoolSemesterDto);
            }

            if (semesterListDto == null || semesterListDto.size() == 0) {
                message = Constant.SEMESTER_LIST_EMPTY;
                responseDto.setMessage(message);
                return responseDto;
            }

            message = Constant.SUCCESS;
            responseDto.setSemesterList(semesterListDto);
            responseDto.setMessage(message);

        } catch (Exception e) {
            message.setMessageCode(1);
            message.setMessage(e.toString());
            responseDto.setMessage(message);
            return responseDto;
        }
        return responseDto;
    }

}
