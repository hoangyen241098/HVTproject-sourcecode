package com.example.webDemo3.service;

import com.example.webDemo3.dto.SearchUserResponseDto;
import com.example.webDemo3.dto.request.SearchUserRequestDto;

public interface SearchUserService {
    SearchUserResponseDto searchUser(SearchUserRequestDto requestModel);
}
