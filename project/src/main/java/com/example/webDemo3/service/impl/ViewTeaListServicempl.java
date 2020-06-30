package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.ViewTeaListResponseDto;
import com.example.webDemo3.dto.request.ViewTeaListRequestDto;
import com.example.webDemo3.entity.Teacher;
import com.example.webDemo3.repository.TeacherRepository;
import com.example.webDemo3.service.ViewTeaListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * lamnt98
 * 30/06
 */
@Service
public class ViewTeaListServicempl implements ViewTeaListService {

    @Autowired
    private TeacherRepository teacherRepository;

    /**
     * lamnt98
     * 30/06
     * search teacher by fullName
     * @param viewTeacherList
     * @return ViewTeaListResponseDto
     */
    @Override
    public ViewTeaListResponseDto searchTeacher(ViewTeaListRequestDto viewTeacherList) {
        ViewTeaListResponseDto viewListResponse = new ViewTeaListResponseDto();
        MessageDTO message = new MessageDTO();
        Pageable paging;
        Integer orderBy = viewTeacherList.getOrderBy();
        Integer pageNumber = viewTeacherList.getPageNumber();
        String fullName = viewTeacherList.getFullName().trim();
        String orderByProperty = "fullName";

        Page<Teacher> pagedResult;
        Integer pageSize = Constant.PAGE_SIZE;

        //check orby asc or desc
        if(orderBy == 0){
            paging = PageRequest.of(pageNumber, pageSize, Sort.by(orderByProperty).descending());
        }
        else {
            paging = PageRequest.of(pageNumber, pageSize, Sort.by(orderByProperty).ascending());
        }

        //check fullName empty or not
        if(fullName.trim().isEmpty()){
            pagedResult = teacherRepository.selectAll(paging);
        }else{
            pagedResult = teacherRepository.searchTeacherBy(fullName,paging);
        }

        //check result when get list
        if(pagedResult.getTotalElements() == 0){
            message = Constant.TEACHERLIST_NULL;
            viewListResponse.setMessage(message);
            return viewListResponse;
        }

        message = Constant.SUCCESS;
        viewListResponse.setMessage(message);
        viewListResponse.setTeacherList(pagedResult);
        return viewListResponse;
    }
}
