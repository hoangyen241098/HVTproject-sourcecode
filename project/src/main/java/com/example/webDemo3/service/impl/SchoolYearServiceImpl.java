package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.DetailSchoolYearResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.SchoolYearResponseDto;
import com.example.webDemo3.dto.SchoolYearTableResponseDto;
import com.example.webDemo3.dto.request.AddSchoolYearRequestDto;
import com.example.webDemo3.dto.request.DelSchoolYearRequestDto;
import com.example.webDemo3.dto.request.DetailSchoolYearRequestDto;
import com.example.webDemo3.dto.request.EditSchoolYearRequestDto;
import com.example.webDemo3.entity.SchoolYear;
import com.example.webDemo3.repository.SchoolYearRepository;
import com.example.webDemo3.service.SchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/*
kimpt142 - 06/07
 */
@Service
public class SchoolYearServiceImpl implements SchoolYearService {

    @Autowired
    private SchoolYearRepository schoolYearRepository;

    /**
     * kimpt142
     * 06/07
     * get all school year in database
     * @return school year list and message
     */
    @Override
    public SchoolYearTableResponseDto getSchoolYearTable() {
        SchoolYearTableResponseDto responseDto = new SchoolYearTableResponseDto();
        MessageDTO message = new MessageDTO();
        List<SchoolYearResponseDto> responseDtoList = new ArrayList<>();
        List<SchoolYear> schoolYearList = schoolYearRepository.findAllSortByFromDate();

        if(schoolYearList == null){
            message = Constant.SCHOOLYEARLIST_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
        }

        try {
            for (SchoolYear item : schoolYearList) {
                SchoolYearResponseDto yearResponseDto = new SchoolYearResponseDto();
                yearResponseDto.setSchoolYearId(item.getYearID());
                yearResponseDto.setYearName(item.getFromYear().toString() + "-" + item.getToYear().toString());
                yearResponseDto.setFromDate(item.getFromDate());
                yearResponseDto.setToDate(item.getToDate());
                responseDtoList.add(yearResponseDto);
            }
        }
        catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            responseDto.setMessage(message);
            return responseDto;
        }

        responseDto.setSchoolYearList(responseDtoList);
        message = Constant.SUCCESS;
        responseDto.setMessage(message);
        return responseDto;
    }

    /**
     * kimpt142
     * 06/07
     * delete school year in db using id
     * @param model include schoolyearid
     * @return messageDto
     */
    @Override
    public MessageDTO deleteSchoolYearById(DelSchoolYearRequestDto model) {
        MessageDTO message = new MessageDTO();
        Integer schoolYearId = model.getSchoolYearId();

        if(schoolYearId == null){
            message = Constant.SCHOOLYEARID_EMPTY;
            return message;
        }

        try {
            schoolYearRepository.deleteById(schoolYearId);
        }catch (Exception e){
            message = Constant.DELETESCHOOLYEAR_FAIL;
            return message;
        }
        message = Constant.SUCCESS;
        return message;
    }

    /**
     * kimpt142
     * 06/07
     * add new school year
     * @param model
     * @return message
     */
    @Override
    public MessageDTO addchoolYear(AddSchoolYearRequestDto model) {
        MessageDTO message = new MessageDTO();
        SchoolYear schoolYear = new SchoolYear();
        Integer fromYear = model.getFromYear();
        Integer toYear = model.getToYear();
        Date fromDate = model.getFromDate();
        Date toDate = model.getToDate();

        if(fromYear==null){
            message = Constant.FROMYEAR_EMPTY;
            return message;
        }

        if(toYear==null){
            message = Constant.TOYEAR_EMPTY;
            return message;
        }

        if(fromDate.toString().equalsIgnoreCase("")){
            message = Constant.FROMDATE_EMPTY;
            return message;
        }

        if(toDate.toString().equalsIgnoreCase("")){
            message = Constant.TODATE_EMPTY;
            return message;
        }

        if(fromDate.after(toDate)){
            message = Constant.FROMDATE_GREATER_TODATE;
            return message;
        }

        SchoolYear schoolYearListByFromDate = schoolYearRepository.findSchoolYearsByDate(fromDate);
        if(schoolYearListByFromDate != null){
            message = Constant.FROMDATE_EXIST;
            return message;
        }

        SchoolYear schoolYearListByToDate = schoolYearRepository.findSchoolYearsByDate(toDate);
        if(schoolYearListByToDate != null){
            message = Constant.TODATE_EXIST;
            return message;
        }

        schoolYear.setFromYear(fromYear);
        schoolYear.setToYear(toYear);
        schoolYear.setFromDate(fromDate);
        schoolYear.setToDate(toDate);

        try {
            schoolYearRepository.save(schoolYear);
        }
        catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }

        message = Constant.SUCCESS;
        return message;
    }

    /**
     * kimpt142
     * 06/07
     * edit school year
     * @param model
     * @return message
     */
    @Override
    public MessageDTO editSchoolYear(EditSchoolYearRequestDto model) {
        MessageDTO message = new MessageDTO();
        SchoolYear schoolYear = new SchoolYear();
        Integer schoolYearId = model.getSchoolYearId();
        Integer fromYear = model.getFromYear();
        Integer toYear = model.getToYear();
        Date fromDate = model.getFromDate();
        Date toDate = model.getToDate();

        if(schoolYearId == null){
            message = Constant.SCHOOLYEARID_EMPTY;
            return message;
        }

        if(fromYear==null){
            message = Constant.FROMYEAR_EMPTY;
            return message;
        }

        if(toYear==null){
            message = Constant.TOYEAR_EMPTY;
            return message;
        }

        if(fromDate.toString().equalsIgnoreCase("")){
            message = Constant.FROMDATE_EMPTY;
            return message;
        }

        if(toDate.toString().equalsIgnoreCase("")){
            message = Constant.TODATE_EMPTY;
            return message;
        }

        if(fromDate.after(toDate)){
            message = Constant.FROMDATE_GREATER_TODATE;
            return message;
        }

        Date dateCurrent = new Date(System.currentTimeMillis());
        SchoolYear schoolCurrent = schoolYearRepository.findById(schoolYearId).orElse(null);
        if(schoolCurrent!=null){
            if(fromDate.after(dateCurrent)){
                message = Constant.FROMDATE_GREATER_CURRENTDATE;
                return message;
            }
        }

        SchoolYear schoolYearListByFromDate = schoolYearRepository.findSchoolYearsByDate(fromDate);
        if(schoolYearListByFromDate != null && schoolYearListByFromDate.getYearID() != schoolYearId){
            message = Constant.FROMDATE_EXIST;
            return message;
        }

        SchoolYear schoolYearListByToDate = schoolYearRepository.findSchoolYearsByDate(toDate);
        if(schoolYearListByToDate != null && schoolYearListByToDate.getYearID() != schoolYearId){
            message = Constant.TODATE_EXIST;
            return message;
        }

        schoolYear.setYearID(schoolYearId);
        schoolYear.setFromYear(fromYear);
        schoolYear.setToYear(toYear);
        schoolYear.setFromDate(fromDate);
        schoolYear.setToDate(toDate);

        try {
            schoolYearRepository.save(schoolYear);
        }
        catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }

        message = Constant.SUCCESS;
        return message;
    }

    /**
     * kimpt142
     * 09/07
     * find school year information by id
     * @param model include yearid
     * @return information of school year and message
     */
    @Override
    public DetailSchoolYearResponseDto getDetailSchoolYearById(DetailSchoolYearRequestDto model) {
        DetailSchoolYearResponseDto responseDto = new DetailSchoolYearResponseDto();
        MessageDTO message = new MessageDTO();
        Integer schoolYearId = model.getSchoolYearId();
        SchoolYear schoolYear = new SchoolYear();

        if(schoolYearId == null){
            message = Constant.SCHOOLYEARID_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
        }

        schoolYear = schoolYearRepository.findById(schoolYearId).orElse(null);

        if(schoolYear == null){
            message = Constant.SCHOOLYEAR_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
        }

        responseDto.setFromYear(schoolYear.getFromYear());
        responseDto.setToYear(schoolYear.getToYear());
        responseDto.setFromDate(schoolYear.getFromDate());
        responseDto.setToDate(schoolYear.getToDate());
        message = Constant.SUCCESS;
        responseDto.setMessage(message);
        return responseDto;
    }
}
