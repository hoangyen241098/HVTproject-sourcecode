package com.example.webDemo3.controller;

import com.example.webDemo3.dto.manageSchoolRankResponseDto.RankMonthListResposeDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ViewMonthListResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.CreateRankMonthRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ListWeekSchoolRankRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.SearchRankMonthRequestDto;
import com.example.webDemo3.service.manageSchoolRankMonthService.ViewSchoolRankMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListWeekSchoolRankResponseDto;
import com.example.webDemo3.service.manageSchoolRankMonthService.CreateAndEditSchoolRankMonthService;
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
     * catch request to get month list
     * @return responseDTO with a month list and messagedto
     */
    @PostMapping("/getmonthlist")
    public ViewMonthListResponseDto getMonthList()
    {
        return viewSchoolRankMonthService.getMonthList();
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
}
