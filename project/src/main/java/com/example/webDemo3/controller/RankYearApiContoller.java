package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListMonthSchoolRankResponseDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListSemesterSchoolRankResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.*;
import com.example.webDemo3.service.manageSchoolRankYearSerivce.CreateAndEditSchoolRankYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
kimpt142 - 23/07
 */
@RestController
@RequestMapping("/api/rankyear")
public class RankYearApiContoller {

    @Autowired
    private CreateAndEditSchoolRankYearService createAndEditSchoolRankYearService;

    /**
     * lamnt98
     * 24/07
     * catch request to get semester list which has no ranked
     * @return ListSemesterSchoolRankResponseDto
     */
    @PostMapping("/loadsemesterlist")
    public ListSemesterSchoolRankResponseDto getSemesterList()
    {
        return createAndEditSchoolRankYearService.loadListSemester();
    }

    /**
     * lamnt98
     * 24/07
     * catch request to get semester list which has no ranked and semester is ranked with yearId
     * @return ListSemesterSchoolRankResponseDto
     */
    @PostMapping("/loadeditrankyear")
    public ListSemesterSchoolRankResponseDto getEditSemesterList(@RequestBody ViewSemesterOfEditRankYearRequestDto module)
    {
        return createAndEditSchoolRankYearService.loadEditListSemester(module);
    }

    /**
     * lamnt98
     * 23/07
     * catch request to creat rank year
     * @return MessageDTO
     */
    @PostMapping("/createrankyear")
    public MessageDTO createRankYear(@RequestBody CreateRankYearRequestDto module)
    {
        return createAndEditSchoolRankYearService.createRankYear(module);
    }

    /**
     * lamnt98
     * 24/07
     * catch request to edit rank year
     * @return MessageDTO
     */
    @PostMapping("/editrankyear")
    public MessageDTO editrankyear(@RequestBody EditRankYearRequestDto module)
    {
        return createAndEditSchoolRankYearService.editRankYear(module);
    }
}
