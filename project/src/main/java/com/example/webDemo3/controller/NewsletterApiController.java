package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.RankMonthListResponseDto;
import com.example.webDemo3.dto.request.manageNewsletterRequestDto.AddNewsletterRequestDto;
import com.example.webDemo3.dto.request.manageNewsletterRequestDto.ConfirmRequestNewsletterDto;
import com.example.webDemo3.dto.request.manageNewsletterRequestDto.EditNewsletterRequestDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.SearchRankMonthRequestDto;
import com.example.webDemo3.service.manageNewsletterService.HandleNewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * lamnt98 - 27/07
 */
@RestController
@RequestMapping("/api/newsletter")
public class NewsletterApiController {

    @Autowired
    private HandleNewsletterService handleNewsletterService;

    /**
     * kimpt142
     * 27/07
     * catch request to add newsletter
     * @return messagedto
     */
    @PostMapping("/addnewsletter")
    public MessageDTO addNewsletter(@RequestBody AddNewsletterRequestDto model)
    {
        return handleNewsletterService.addNewsletter(model);
    }

    /**
     * kimpt142
     * 27/07
     * catch request to edit newsletter
     * @return messagedto
     */
    @PostMapping("/editnewsletter")
    public MessageDTO editNewsletter(@RequestBody EditNewsletterRequestDto model)
    {
        return handleNewsletterService.editNewsletter(model);
    }

    /**
     * kimpt142
     * 27/07
     * catch request to confirm request newsletter
     * @return messagedto
     */
    @PostMapping("/confirmnewsletter")
    public MessageDTO confirmRequestNewsletter(@RequestBody ConfirmRequestNewsletterDto model)
    {
        return handleNewsletterService.confirmRequestNewsletter(model);
    }
}
