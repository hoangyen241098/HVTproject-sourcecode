package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.*;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.LoadByYearIdRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.SearchRankSemesterRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.UpdateSchoolRankWeekRequestDto;
import com.example.webDemo3.service.manageSchoolRank.ViewSchoolRankWeekService;
import com.example.webDemo3.service.manageSchoolRankSemesterService.ViewSchoolRankSemesterService;
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
    private ViewSchoolRankSemesterService viewSchoolRankSemesterService;

    /**
     * kimpt142
     * 24/07
     * catch request to get year list and semester list by year id
     * @param model include current year id
     * @return MessageDTO
     */
    @PostMapping("/loadranksemester")
    public LoadRankSemesterResponseDto loadRankSemester(@RequestBody LoadByYearIdRequestDto model)
    {
        return viewSchoolRankSemesterService.loadRankSemesterPage(model);
    }

    /**
     * kimpt142
     * 24/07
     * catch request to get semester list by year id
     * @param model include year id
     * @return ViewWeekListResponseDto
     */
    @PostMapping("/getsemesterlist")
    public ViewSemesterListResponseDto getSemesterByYearId(@RequestBody LoadByYearIdRequestDto model)
    {
        return viewSchoolRankSemesterService.getSemesterListByYearId(model);
    }

    /**
     * kimpt142
     * 24/07
     * catch request to get rank semester list by semester id
     * @param model include semester id
     * @return RankSemesterListResponseDto
     */
    @PostMapping("/searchranksemester")
    public RankSemesterListResponseDto getSemesterByYearId(@RequestBody SearchRankSemesterRequestDto model)
    {
        return viewSchoolRankSemesterService.searchRankSemesterById(model);
    }
}
