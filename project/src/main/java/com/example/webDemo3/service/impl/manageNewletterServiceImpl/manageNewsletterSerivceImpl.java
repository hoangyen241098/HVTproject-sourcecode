package com.example.webDemo3.service.impl.manageNewletterServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageNewsletterResponseDto.NewsletterListResponseDto;
import com.example.webDemo3.dto.request.manageNewsletterRequestDto.LoadHomePageRequestDto;
import com.example.webDemo3.entity.Newsletter;
import com.example.webDemo3.repository.NewsletterRepository;
import com.example.webDemo3.service.manageNewsletterService.manageNewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * lamnt98 - 27/07
 */
@Service
public class manageNewsletterSerivceImpl implements manageNewsletterService {

    @Autowired
    private NewsletterRepository newsletterRepository;

    @Override
    public NewsletterListResponseDto getAllLetter(LoadHomePageRequestDto requestDto) {

        NewsletterListResponseDto responseDto = new NewsletterListResponseDto();

        Integer pageNumber = requestDto.getPageNumber();
        MessageDTO message = new MessageDTO();

        String orderByProperty = "createDate";
        Page<Newsletter> pagedResult = null;
        Pageable paging;
        Integer pageSize = Constant.PAGE_SIZE;

        try{

            //check pageNumber null or not
            if(pageNumber == null){
                pageNumber = 0;
            }

            paging = PageRequest.of(pageNumber, pageSize, Sort.by(orderByProperty).descending());

            pagedResult = newsletterRepository.loadAllLetter(paging);

            //check result when get list
            if(pagedResult==null || pagedResult.getTotalElements() == 0){
                message = Constant.NEWSLETTERLIST_EMPTY;
                responseDto.setMessage(message);
                return responseDto;
            }

            message = Constant.SUCCESS;
            responseDto.setListLetter(pagedResult);
            responseDto.setMessage(message);

        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            responseDto.setMessage(message);
        }
        return responseDto;
    }
}
