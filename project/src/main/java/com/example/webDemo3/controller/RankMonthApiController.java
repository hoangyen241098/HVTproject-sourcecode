package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListDateResponseDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListWeekSchoolRankResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.*;
import com.example.webDemo3.service.manageSchoolRankMonthService.CreateAndEditSchoolRankMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
kimpt142 - 23/07
 */
@RestController
@RequestMapping("/api/rankmonth")
public class RankMonthApiController {

    @Autowired
    private CreateAndEditSchoolRankMonthService createAndEditSchoolRankMonthService;

    /**
     * lamnt98
     * 23/07
     * catch request to get week list which has no ranked
     * @return ListWeekSchoolRankResponseDto
     */
    @PostMapping("/loadweeklist")
    public ListWeekSchoolRankResponseDto getWeekList(@RequestBody ListWeekSchoolRankRequestDto module)
    {
        return createAndEditSchoolRankMonthService.loadListWeek(module);
    }

    /**
     * lamnt98
     * 23/07
     * catch request to creat rank month
     * @return MessageDTO
     */
    @PostMapping("/createrankmonth")
    public MessageDTO createRankMonth(@RequestBody CreateRankMonthRequestDto module)
    {
        return createAndEditSchoolRankMonthService.createRankMonth(module);
    }

    /**
     * lamnt98
     * 23/07
     * catch request to get week list which has no ranked and week list has ranked by mothId
     * @return ListWeekSchoolRankResponseDto
     */
    @PostMapping("/loadeditrankmonth")
    public ListWeekSchoolRankResponseDto getWeekListEdit(@RequestBody ViewWeekOfEditRankMontRequestDto module)
    {
        return createAndEditSchoolRankMonthService.loadEditListWeek(module);
    }

    /**
     * lamnt98
     * 23/07
     * catch request to edit rank month
     * @return MessageDTO
     */
    @PostMapping("/editrankmonth")
    public MessageDTO editRankMonth(@RequestBody EditRankMonthRequestDto module)
    {
        return createAndEditSchoolRankMonthService.editRankMonth(module);
    }
}
