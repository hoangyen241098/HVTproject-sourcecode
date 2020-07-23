package com.example.webDemo3.service.impl.manageSchoolRankMonthImpl;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageSchoolRankResponseDto.ListWeekSchoolRankResponseDto;
import com.example.webDemo3.dto.request.manageSchoolRankRequestDto.ListWeekSchoolRankRequestDto;
import com.example.webDemo3.repository.SchoolRankMonthRepository;
import com.example.webDemo3.service.manageSchoolRankMonthService.CreateAndEditSchoolRankMonthService;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateAndEditSchoolRankMonthServiceImpl implements CreateAndEditSchoolRankMonthService {

    @Autowired
    private SchoolRankMonthRepository schoolRankMonthRepository;

    @Override
    public ListWeekSchoolRankResponseDto loadListWeek(ListWeekSchoolRankRequestDto requestDto) {
        ListWeekSchoolRankResponseDto responseDto = new ListWeekSchoolRankResponseDto();
        MessageDTO message = new MessageDTO();

        try{

        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            responseDto.setMessage(message);
            return responseDto;
        }

        return  responseDto;
    }
}
