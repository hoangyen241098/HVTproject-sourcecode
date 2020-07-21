package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListDateResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.CreateRankWeekRequestDto;
import com.example.webDemo3.service.manageSchoolRank.CreateAndEditSchoolRankWeekService;
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
    private CreateAndEditSchoolRankWeekService createAndEditSchoolRankWeekService;

    @Autowired
    private ViewSchoolRankWeekService viewSchoolRankWeekService;

    @Autowired
    private UpdateSchoolRankWeekService updateSchoolRankWeekService;

    /**
     * lamnt98
     * 21/07
     * catch request to get date list which has no ranked
     * @return reponseDTO with a class list and messagedto
     */
    @PostMapping("/loaddatelist")
    public ListDateResponseDto getDateList()
    {
        return createAndEditSchoolRankWeekService.loadListDate();
    }

    /**
     * lamnt98
     * 21/07
     * catch request to get create rank week
     * @return reponseDTO
     */
    @PostMapping("/createrankweek")
    public MessageDTO createRankWeek(@RequestBody CreateRankWeekRequestDto module) {

        return createAndEditSchoolRankWeekService.createRankWeek(module);
    }

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
     * catch request to update school rank week list
     * @param model include rank week list
     * @return MessageDTO
     */
    @PostMapping("/updatescorerankweek")
    public MessageDTO updateRankWeek(@RequestBody UpdateSchoolRankWeekRequestDto model)
    {
        return updateSchoolRankWeekService.updateSchoolRankWeek(model);
    }
}
