package com.example.webDemo3.controller;

import com.example.webDemo3.dto.ClassListResponseDto;
import com.example.webDemo3.dto.LoginResponseDto;
import com.example.webDemo3.dto.RoleListResponseDTO;
import com.example.webDemo3.dto.request.LoginRequestDto;
import com.example.webDemo3.service.ClassListService;
import com.example.webDemo3.service.LoginService;
import com.example.webDemo3.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/admin")
public class RoleListController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ClassListService classListService;

    /**
     * kimpt142
     * 25/6
     * catch request to get role list
     * @return reponseDTO with a role list and messagedto
     */
    @PostMapping("/rolelist")
    public RoleListResponseDTO getRoleList()
    {
        RoleListResponseDTO roleDto = new RoleListResponseDTO();
        roleDto = roleService.getAllRole();
        return  roleDto;
    }

    /**
     * kimpt142
     * 28/6
     * catch request to get class list
     * @return reponseDTO with a class list and messagedto
     */
    @PostMapping("/classlist")
    public ClassListResponseDto getClassList()
    {
        ClassListResponseDto classDto = new ClassListResponseDto();
        classDto = classListService.getClassList();
        return  classDto;
    }
}
