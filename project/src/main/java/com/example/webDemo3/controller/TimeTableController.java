package com.example.webDemo3.controller;

import com.example.webDemo3.dto.ClassTimeTableResponseDto;
import com.example.webDemo3.dto.ListWeekResponseDto;
import com.example.webDemo3.dto.ListYearAndClassResponseDto;
import com.example.webDemo3.dto.request.ClassTimeTableRequestDto;
import com.example.webDemo3.dto.request.ListWeekRequestDto;
import com.example.webDemo3.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timetable")
public class TimeTableController {

    @Autowired
    private TimeTableService viewTimTaClassService;

    /**
     * lamnt98
     * 01/07
     * catch request from client to send list year, week , yearIdCurrent, weekIdCurrent
     * @param
     * @return ListYearAndClassResponseDto
     */
    @PostMapping("/listyearandclass")
    public ListYearAndClassResponseDto getlistyearandclass()
    {
        return viewTimTaClassService.getListYearAndClass();
    }

    /**
     * lamnt98
     * 01/07
     * catch request from client to find list week by yearIdCurrent
     * @param listWeekRequestDto
     * @return ListWeekResponseDto
     */
    @PostMapping("/listweek")
    public ListWeekResponseDto getlistweek(@RequestBody ListWeekRequestDto listWeekRequestDto)
    {
        return viewTimTaClassService.getListWeekByYearId(listWeekRequestDto);
    }

    /**
     * lamnt98
     * 01/07
     * catch request from client to find class timetable by weekId and classId
     * @param classTimeTableRequestDto
     * @return ClassTimeTableResponseDto
     */
    @PostMapping("/classtimetable")
    public ClassTimeTableResponseDto getClassTimeTable(@RequestBody ClassTimeTableRequestDto classTimeTableRequestDto)
    {
        return viewTimTaClassService.getClassTimeTable(classTimeTableRequestDto);
    }
}
