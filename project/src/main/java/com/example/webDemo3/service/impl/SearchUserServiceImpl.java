package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.SearchUserResponseDto;
import com.example.webDemo3.dto.request.SearchUserRequestDto;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.SearchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchUserServiceImpl implements SearchUserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * kimpt142
     * 28/6
     * search username with multiple condition
     * @param requestModel
     * @return
     */
    @Override
    public SearchUserResponseDto searchUser(SearchUserRequestDto requestModel) {
        SearchUserResponseDto responseDto = new SearchUserResponseDto();
        Pageable paging;
        Integer orderBy = requestModel.getOrderBy();
        Integer pageNumber = requestModel.getPageNumber();
        String orderByProperty;
        Integer pageSize = Constant.PAGE_SIZE;
        switch (orderBy){
            case 0: {
                orderByProperty = "username";
                break;
            }
            case 1: {
                orderByProperty = "classSchool.grade";
                break;
            }
            case 2: {
                orderByProperty = "name";
                break;
            }
            default: orderByProperty = "username";
        }

        if(requestModel.getSortBy() == 0){
            paging = PageRequest.of(pageNumber, pageSize, Sort.by(orderByProperty).descending());
        }
        else {
            paging = PageRequest.of(pageNumber, pageSize, Sort.by(orderByProperty).ascending());
        }
        String username = requestModel.getUserName();
        Page<User> pagedResult = userRepository.searchUserByCondition(username,requestModel.getRoleId(), paging);
        responseDto.setUserList(pagedResult);
        return responseDto;
    }
}
