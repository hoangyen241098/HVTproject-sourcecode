package com.example.webDemo3.controller;

import com.example.webDemo3.dto.RoleListResponseDTO;
import com.example.webDemo3.dto.SearchUserResponseDto;
import com.example.webDemo3.dto.request.SearchUserRequestDto;
import com.example.webDemo3.service.SearchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class SearchUserController {
    @Autowired
    private SearchUserService searchUserService;

    @PostMapping("/userlist")
    public SearchUserResponseDto getRoleList(@RequestBody SearchUserRequestDto model)
    {
        SearchUserResponseDto responseDto = new SearchUserResponseDto();
        responseDto = searchUserService.searchUser(model);
        return  responseDto;
    }
}
