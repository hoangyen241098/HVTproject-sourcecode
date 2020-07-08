package com.example.webDemo3.controller;

import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.ClassTimeTableRequestDto;
import com.example.webDemo3.dto.request.ListWeekRequestDto;
import com.example.webDemo3.dto.request.TeacherTimeTableRequestDto;
import com.example.webDemo3.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timetable")
public class TimeTableApiController {

//    @Autowired
//    private TimeTableService viewTimTaClassService;
//
//    /**
//     * lamnt98
//     * 01/07
//     * catch request from client to send list year, week, class  and yearIdCurrent, weekIdCurrent
//     * @param
//     * @return ListYearAndClassResponseDto
//     */
//    @PostMapping("/listyearandclass")
//    public ListYearAndClassResponseDto getlistyearandclass()
//    {
//        return viewTimTaClassService.getListYearAndClass();
//    }
//
//    /**
//     * lamnt98
//     * 01/07
//     * catch request from client to find list week by yearIdCurrent
//     * @param listWeekRequestDto
//     * @return ListWeekResponseDto
//     */
//    @PostMapping("/listweek")
//    public ListWeekResponseDto getlistweek(@RequestBody ListWeekRequestDto listWeekRequestDto)
//    {
//        return viewTimTaClassService.getListWeekByYearId(listWeekRequestDto);
//    }
//
//    /**
//     * lamnt98
//     * 01/07
//     * catch request from client to find class timetable by weekId and classId
//     * @param classTimeTableRequestDto
//     * @return TimeTableResponseDto
//     */
//    @PostMapping("/classtimetable")
//    public TimeTableResponseDto getClassTimeTable(@RequestBody ClassTimeTableRequestDto classTimeTableRequestDto)
//    {
//        return viewTimTaClassService.getClassTimeTable(classTimeTableRequestDto);
//    }
//
//    /**
//     * lamnt98
//     * 02/07
//     * catch request from client to send list year, week , teacher and  yearIdCurrent, weekIdCurrent
//     * @param
//     * @return ListYearAndTeacherResponseDto
//     */
//    @PostMapping("/listyearandteacher")
//    public ListYearAndTeacherResponseDto getlistyearandteacher()
//    {
//        return viewTimTaClassService.getListYearAndTeacher();
//    }
//
//    /**
//     * lamnt98
//     * 02/07
//     * catch request from client to find tacher timetable by weekId and teacherId
//     * @param teacherTimeTableRequestDto
//     * @return TimeTableResponseDto
//     */
//    @PostMapping("/teachertimetable")
//    public TimeTableResponseDto getTeacherTimeTable(@RequestBody TeacherTimeTableRequestDto teacherTimeTableRequestDto)
//    {
//        return viewTimTaClassService.getTeacherTimeTable(teacherTimeTableRequestDto);
//    }
}
