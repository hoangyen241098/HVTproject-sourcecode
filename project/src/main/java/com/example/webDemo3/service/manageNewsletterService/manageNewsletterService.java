package com.example.webDemo3.service.manageNewsletterService;

import com.example.webDemo3.dto.manageNewsletterResponseDto.NewsletterListResponseDto;
import com.example.webDemo3.dto.request.manageNewsletterRequestDto.LoadHomePageRequestDto;

/**
 * lamnt98 - 27/07
 */
public interface manageNewsletterService {
    public NewsletterListResponseDto getAllLetter(LoadHomePageRequestDto requestDto);
}
