package com.example.webDemo3.controller;

import com.example.webDemo3.dto.manageNewsletterResponseDto.NewsletterListResponseDto;
import com.example.webDemo3.dto.manageNewsletterResponseDto.ViewDetailLetterResponseDto;
import com.example.webDemo3.dto.request.manageNewsletterRequestDto.LoadHomePageRequestDto;
import com.example.webDemo3.dto.request.manageNewsletterRequestDto.SearchLetterRequestDto;
import com.example.webDemo3.dto.request.manageNewsletterRequestDto.ViewDetailLetterRequestDto;
import com.example.webDemo3.service.manageNewsletterService.manageNewsletterService;
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
    private manageNewsletterService manageNewsletterService;

    /**
     * lamnt98
     * 27/07
     * catch request from client to get all letter
     * @param model
     * @return NewsletterListResponseDto
     */
    @PostMapping("/loadhomepage")
    public NewsletterListResponseDto loadHomePage(@RequestBody LoadHomePageRequestDto model)
    {
        return  manageNewsletterService.getAllLetter(model);
    }

    /**
     * lamnt98
     * 27/07
     * catch request from client to search letter
     * @param model
     * @return NewsletterListResponseDto
     */
    @PostMapping("/searchletter")
    public NewsletterListResponseDto searchLetter(@RequestBody SearchLetterRequestDto model)
    {
        return  manageNewsletterService.searchLetter(model);
    }

    /**
     * lamnt98
     * 27/07
     * catch request from client to view detail letter
     * @param model
     * @return ViewDetailLetterResponseDto
     */
    @PostMapping("/viewletter")
    public ViewDetailLetterResponseDto viewLetter(@RequestBody ViewDetailLetterRequestDto model)
    {
        return  manageNewsletterService.viewLetter(model);
    }
}
