package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.ClassRedStarResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.ViewAssignTaskResponseDto;
import com.example.webDemo3.dto.request.ViewAssignTaskRequestDto;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.ClassRedStar;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.ClassRedStarRepository;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private ClassRedStarRepository classRedStarRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ViewAssignTaskResponseDto viewTask(ViewAssignTaskRequestDto assignTaskRequestDto) {
        ViewAssignTaskResponseDto assignTaskResponseDto = new ViewAssignTaskResponseDto();

        List<ClassRedStarResponseDto> list = new ArrayList<>();
        List<Class> listClass = new ArrayList<>();
        List<User> listRedStar = new ArrayList<>();
        List<Date> listDate = new ArrayList<>();

        Page<ClassRedStar> pagedResult = null;
        Pageable paging;
        Integer pageSize = Constant.PAGE_SIZE;

        MessageDTO message = new MessageDTO();
        Date biggetDate = null;

        Integer pageNumber = assignTaskRequestDto.getPageNumber();
        Date fromDate = assignTaskRequestDto.getFromDate();
        Integer orderBy = assignTaskRequestDto.getOrderBy();
        Integer sortBy = assignTaskRequestDto.getSortBy();
        Integer classId = assignTaskRequestDto.getClassId();
        String redStar = assignTaskRequestDto.getRedStar();

        String orderByProperty;
        //catch sortBy
        switch (sortBy){
            case 0: {
                orderByProperty = "classId";
                break;
            }
            case 1: {
                orderByProperty = "redStar";
                break;
            }
            default: orderByProperty = "classId";
        }

        try{
            listClass = classRepository.findAll();
            listRedStar = userRepository.findAllByRoleRoleId(3);
            listDate = classRedStarRepository.findDistinctByClassRedStarId_FROM_DATE();

            assignTaskResponseDto.setListClass(listClass);
            assignTaskResponseDto.setListRedStar(listRedStar);
            assignTaskResponseDto.setListDate(listDate);

            //catch orderBy
            if(orderBy == 0){
                paging = PageRequest.of(pageNumber, pageSize, Sort.by(orderByProperty).descending());
            }else{
                paging = PageRequest.of(pageNumber, pageSize, Sort.by(orderByProperty).ascending());
            }

            //check fromDate null or not
            if(fromDate == null){
                fromDate = listDate.get(0);
            }

            //check classId null or not
            if(classId == null || classId == 0){

                //check redStar empty or not
                    if(redStar.trim().isEmpty()){
                        pagedResult = classRedStarRepository.findByClassRedStarId_FROM_DATE(fromDate,paging);
                    }else {
                        biggetDate = classRedStarRepository.getBiggestClosetDateRedStar(fromDate,redStar);
                        pagedResult = classRedStarRepository.findByClassRedStarId_FROM_DATEAndClassRedStarId_RED_STAR(biggetDate,redStar,paging);
                    }
                }else{
                //check redStar empty or not
                    if(redStar.trim().isEmpty()){
                        biggetDate = classRedStarRepository.getBiggestClosetDateClassId(fromDate,classId);
                        pagedResult = classRedStarRepository.findByClassIdAndClassRedStarId_FROM_DATE(classId,biggetDate,paging);
                    }else {
                        biggetDate = classRedStarRepository.getBiggestClosetDateClassIdRedStar(fromDate,classId,redStar);
                        pagedResult = classRedStarRepository.findByClassIdAndClassRedStarId_FROM_DATEAndClassRedStarId_RED_STAR(classId,biggetDate,redStar,paging);
                    }
                }

            //check result when get list
            if(pagedResult.getTotalElements() == 0){
                message = Constant.CLASS_RED_STAR_EMPTY;
                assignTaskResponseDto.setMessage(message);
                return assignTaskResponseDto;
            }

            //conver classRedStar from entiti to Dto
            for(ClassRedStar classRedStar : pagedResult){
                ClassRedStarResponseDto classRedStarResponseDto = new ClassRedStarResponseDto();
                classRedStarResponseDto.setClassIdentifier(classRepository.findByClassId(classRedStar.getClassId()).getClassIdentifier());
                classRedStarResponseDto.setRedStar(classRedStar.getClassRedStarId().getRED_STAR());
                list.add(classRedStarResponseDto);
            }
            message = Constant.SUCCESS;
            assignTaskResponseDto.setListAssignTask(list);
            assignTaskResponseDto.setMessage(message);
        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            assignTaskResponseDto.setMessage(message);
            return assignTaskResponseDto;
        }
        return assignTaskResponseDto;
    }

}
