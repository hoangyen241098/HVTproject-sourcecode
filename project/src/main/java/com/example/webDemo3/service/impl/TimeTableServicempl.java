package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.ListWeekResponseDto;
import com.example.webDemo3.dto.ListYearAndClassResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.ListWeekRequestDto;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.SchoolWeek;
import com.example.webDemo3.entity.SchoolYear;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.repository.SchoolWeekRepository;
import com.example.webDemo3.repository.SchoolYearRepository;
import com.example.webDemo3.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * lamnt98
 * 01/07
 */
@Service
public class TimeTableServicempl implements TimeTableService {

    @Autowired
    private SchoolWeekRepository schoolWeek;

    @Autowired
    private SchoolYearRepository schoolYear;

    @Autowired
    private ClassRepository classRepository;

    /**
     * lamnt98
     * 01/07
     * list year, week and find yearIdCurrent, weekIdCurrent
     * @param
     * @return ListYearAndClassResponseDto
     */
    @Override
    public ListYearAndClassResponseDto getListYearAndClass() {
        ListYearAndClassResponseDto list = new ListYearAndClassResponseDto();
        List<SchoolYear> listYear = null;
        List<SchoolWeek> listWeek = null;
        Integer yearIdCurrent = null;
        Integer weekIdCurrent = null;
        List<Class> classList = null;
        MessageDTO messageDTO = new MessageDTO();

        //listWeek = schoolWeek.findAll();
        listYear = schoolYear.findAll();
        if(listYear == null){
            messageDTO = Constant.LIST_YEAR_NULL;
            list.setMessageDTO(messageDTO);
            return list;
        }
        list.setListYear(listYear);

        Date date = Date.valueOf(LocalDate.now());
        for(int i = 0; i < listYear.size(); i++){
            SchoolYear schoolYear = listYear.get(i);
            //find yearIdCurrent
            if(date.compareTo(schoolYear.getFromDate()) > 0 && date.compareTo(schoolYear.getToDate()) < 0){
                yearIdCurrent = schoolYear.getYearID();
            }
        }
        //check yearIdCurrent null or not
        if(yearIdCurrent == null){
            messageDTO = Constant.YEAR_ID_NULL;
            list.setMessageDTO(messageDTO);
            return list;
        }
        list.setYearIdCurrent(yearIdCurrent);

        list.setYearIdCurrent(yearIdCurrent);
        listWeek = schoolWeek.findSchoolWeeksByYearID(yearIdCurrent);

        //check listWeek null or not
        if(listWeek == null){
            messageDTO = Constant.LIST_WEEK_NULL;
            list.setMessageDTO(messageDTO);
            return list;
        }
        list.setListWeek(listWeek);


        for(int i = 0; i < listWeek.size(); i++){
            SchoolWeek schoolWeek = listWeek.get(i);
            //find weekIdCurrent
            if(date.compareTo(schoolWeek.getFromDate()) > 0 && date.compareTo(schoolWeek.getToDate()) < 0){
                weekIdCurrent = schoolWeek.getWeekID();
                break;
            }
        }

        //check weekIdCurrent null or not
        if(weekIdCurrent == null){
            messageDTO = Constant.WEEK_ID_NULL;
            list.setMessageDTO(messageDTO);
            return list;
        }
        list.setWeekIdCurrent(weekIdCurrent);


        classList = classRepository.findAll();

        //check classList null or not
        if(classList==null)
        {
            messageDTO = Constant.CLASSLIST_NOT_EXIT;
            list.setMessageDTO(messageDTO);
            return list;
        }

        list.setClassList(classList);
        messageDTO = Constant.SUCCESS;
        list.setMessageDTO(messageDTO);
        return list;
    }

    /**
     * lamnt98
     * 01/0
     * find list week by yearIdCurrent
     * @param listWeekRequestDto
     * @return ListWeekResponseDto
     */
    @Override
    public ListWeekResponseDto getListWeekByYearId(ListWeekRequestDto listWeekRequestDto) {
        Integer yearIdCurrent = listWeekRequestDto.getYearIdCurrent();
        ListWeekResponseDto listWeekResponseDto = new ListWeekResponseDto();
        List<SchoolWeek> listWeek;
        MessageDTO messageDTO = new MessageDTO();

        //check yearIdCurrent null or not
        if(yearIdCurrent == null){
            messageDTO = Constant.YEAR_ID_NULL;
            listWeekResponseDto.setMessageDTO(messageDTO);
            return listWeekResponseDto;
        }

        listWeek = schoolWeek.findSchoolWeeksByYearID(yearIdCurrent);

        //check listWeek null or not
        if(listWeek.size() == 0){
            messageDTO = Constant.LIST_WEEK_NULL;
            listWeekResponseDto.setMessageDTO(messageDTO);
            return listWeekResponseDto;
        }
        listWeekResponseDto.setListWeek(listWeek);
        messageDTO = Constant.SUCCESS;
        listWeekResponseDto.setMessageDTO(messageDTO);
        return listWeekResponseDto;
    }
}
