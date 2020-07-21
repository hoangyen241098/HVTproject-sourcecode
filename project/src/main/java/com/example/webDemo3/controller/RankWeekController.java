package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListDateResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.CreateRankWeekRequestDto;
import com.example.webDemo3.service.manageSchoolRank.CreateAndEditSchoolRankWeekService;
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
    public MessageDTO createRankWeek(@RequestBody CreateRankWeekRequestDto module)
    {

        return createAndEditSchoolRankWeekService.createRankWeek(module);
    }
}
