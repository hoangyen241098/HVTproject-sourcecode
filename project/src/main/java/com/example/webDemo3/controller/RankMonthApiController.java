package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListWeekSchoolRankResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.*;
import com.example.webDemo3.service.manageSchoolRankMonthService.CreateAndEditSchoolRankMonthService;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.RankMonthListResposeDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ViewMonthListResponseDto;
import com.example.webDemo3.service.manageSchoolRankMonthService.ViewSchoolRankMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

/*
kimpt142 - 23/07
 */
@RestController
@RequestMapping("/api/rankmonth")
public class RankMonthApiController {

    @Autowired
    private ViewSchoolRankMonthService viewSchoolRankMonthService;

    @Autowired
    private CreateAndEditSchoolRankMonthService createAndEditSchoolRankMonthService;

    /**
     * kimpt142
     * 23/07
     * catch request to get month list by year id
     * @return responseDTO with a month list and messagedto
     */
    @PostMapping("/getmonthlist")
    public ViewMonthListResponseDto getMonthListByYearId(@RequestBody ViewMonthByYearRequestDto model)
    {
        return viewSchoolRankMonthService.getMonthListByYearId(model);
    }

    /**
     * kimpt142
     * 23/07
     * catch request to get month list
     * @return responseDTO with a month list and messagedto
     */
    @PostMapping("/searchrankmonth")
    public RankMonthListResposeDto searchRankMonthListById(@RequestBody SearchRankMonthRequestDto model)
    {
        return viewSchoolRankMonthService.searchRankMonthByMonthId(model);
    }

    /**
     * kimpt142
     * 23/07
     * catch request to update school rank week list
     * @param model include rank week list
     * @return MessageDTO
     */
    @PostMapping("/download")
    public ResponseEntity<InputStreamResource> download(@RequestBody SearchRankMonthRequestDto model) {
        ByteArrayInputStream in = viewSchoolRankMonthService.downloadRankMonth(model);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Bangxephangthang.xls");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

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
     * kimpt142
     * 23/07
     * catch request to get school year list and month list by current year id
     * @return responseDTO with a month list and messagedto
     */
    @PostMapping("/loadrankmonth")
    public LoadRankMonthResponseDto loadRankMonth(@RequestBody ViewMonthByYearRequestDto model) {
        return viewSchoolRankMonthService.loadRankMonthPage(model);
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
