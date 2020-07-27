package com.example.webDemo3.service.manageNewsletterService;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.manageNewsletterRequestDto.AddNewsletterRequestDto;
import com.example.webDemo3.dto.request.manageNewsletterRequestDto.ConfirmRequestNewsletterDto;
import com.example.webDemo3.dto.request.manageNewsletterRequestDto.EditNewsletterRequestDto;

/*
kimpt142 - 27/07
 */
public interface HandleNewsletterService {
    MessageDTO addNewsletter(AddNewsletterRequestDto model);
    MessageDTO editNewsletter(EditNewsletterRequestDto model);
    MessageDTO confirmRequestNewsletter(ConfirmRequestNewsletterDto model);
}
