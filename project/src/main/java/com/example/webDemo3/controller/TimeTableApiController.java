package com.example.webDemo3.controller;

import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.ClassTimeTableRequestDto;
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

    @Autowired
    private TimeTableService viewTimTaClassService;



    /**
     * lamnt98
     * 09/07
     * catch request from client to view class timetable by currentDate and classId default
     * @param
     * @return ViewClassTimeTableResponseDto
     */
    @PostMapping("/viewclasstimetable")
    public ViewClassTimeTableResponseDto viewClassTimeTable()
    {
        return viewTimTaClassService.viewClassTimeTable();
    }

    /**
     * lamnt98
     * 09/07
     * catch request from client to view teacher timetable by currentDate and teacherId default
     * @param
     * @return ViewTeacherTimeTableReponseDto
     */
    @PostMapping("/viewteachertimetable")
    public ViewTeacherTimeTableReponseDto getTeacherTimeTable()
    {
        return viewTimTaClassService.viewTeacherTimeTable();
    }

    /**
     * lamnt98
     * 09/07
     * catch request from client to search class timetable by currentDate and classId
     * @param module
     * @return SearchTimeTableResponseDto
     */
    @PostMapping("/searchclasstimetable")
    public SearchTimeTableResponseDto searchClassTimeTable(@RequestBody ClassTimeTableRequestDto module)
    {
        return viewTimTaClassService.searchClassTimeTable(module);
    }

    /**
     * lamnt98
     * 09/07
     * catch request from client to search teacher timetable by currentDate and teacherId
     * @param module
     * @return SearchTimeTableResponseDto
     */
    @PostMapping("/searchteachertimetable")
    public SearchTimeTableResponseDto searchTeacherTimeTable(@RequestBody TeacherTimeTableRequestDto module)
    {
        return viewTimTaClassService.searchTeacherTimeTable(module);
    }
}
