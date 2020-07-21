package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.RankWeekListResponseDto;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ViewWeekAndClassListResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.SearchRankWeekRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.UpdateSchoolRankWeekRequestDto;
import com.example.webDemo3.service.manageSchoolRank.UpdateSchoolRankWeekService;
import com.example.webDemo3.service.manageSchoolRank.ViewSchoolRankWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
kimpt142 - 21/07
 */
@RestController
@RequestMapping("/api/rankweek")
public class RankWeekController {

    @Autowired
    private ViewSchoolRankWeekService viewSchoolRankWeekService;

    @Autowired
    private UpdateSchoolRankWeekService updateSchoolRankWeekService;

    /**
     * kimpt142
     * 21/07
     * catch request to get the week list and class list
     * @param
     * @return ViewWeekAndClassListResponseDto
     */
    @PostMapping("/viewweekandclasslist")
    public ViewWeekAndClassListResponseDto getSchoolYearList()
    {
        return viewSchoolRankWeekService.getWeekAndClassList();
    }

    /**
     * kimpt142
     * 21/07
     * catch request to get school rank week list by week id and class id
     * @param model include week id and class id
     * @return RankWeekListResponseDto
     */
    @PostMapping("/searchrankweek")
    public RankWeekListResponseDto searchRankWeekByWeekAndClass(@RequestBody SearchRankWeekRequestDto model)
    {
        return viewSchoolRankWeekService.searchRankWeekByWeekAndClass(model);
    }

    /**
     * kimpt142
     * 21/07
     * catch request to get school rank week list by week id and class id
     * @param model include week id and class id
     * @return RankWeekListResponseDto
     */
    @PostMapping("/updatescorerankweek")
    public MessageDTO updateRankWeek(@RequestBody UpdateSchoolRankWeekRequestDto model)
    {
        return updateSchoolRankWeekService.updateSchoolRankWeek(model);
    }
}
