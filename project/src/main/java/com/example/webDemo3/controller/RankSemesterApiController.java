package com.example.webDemo3.controller;

import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListMonthSchoolRankResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ListMonthSchoolRankRequestDto;
import com.example.webDemo3.service.manageSchoolRankSemesterService.CreateAndEditSchoolRankSemester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
kimpt142 - 23/07
 */
@RestController
@RequestMapping("/api/ranksemester")
public class RankSemesterApiController {

    @Autowired
    private CreateAndEditSchoolRankSemester createAndEditSchoolRankSemester;
    /**
     * lamnt98
     * 23/07
     * catch request to get month list which has no ranked
     * @return ListMonthSchoolRankResponseDto
     */
    @PostMapping("/loadmonthlist")
    public ListMonthSchoolRankResponseDto getMonthList(@RequestBody ListMonthSchoolRankRequestDto module)
    {
        return createAndEditSchoolRankSemester.loadListMonth(module);
    }
}
