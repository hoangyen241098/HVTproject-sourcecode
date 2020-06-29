package com.example.webDemo3.controller;

import com.example.webDemo3.dto.ClassListResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.RoleListResponseDTO;
import com.example.webDemo3.dto.SearchUserResponseDto;
import com.example.webDemo3.dto.request.AddAccResquestDTO;
import com.example.webDemo3.dto.request.DeleteAccountRequestDto;
import com.example.webDemo3.dto.request.ResetPassRequestDTO;
import com.example.webDemo3.dto.request.SearchUserRequestDto;
import com.example.webDemo3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AddAccountService addAccountService;

    @Autowired
    private SearchUserService searchUserService;

    @Autowired
    private DeleteAccountService deleteAccountService;

    @Autowired
    private ResetPassService resetPassService;

    /**
     * kimpt142
     * 27/6/2020
     * catch request from client to add new account
     * @param model include all property of user table
     * @return MessageDTO with (1,success) if success
     */
    @PostMapping("/createaccount")
    public MessageDTO login(@RequestBody AddAccResquestDTO model)
    {
        return addAccountService.addAccount(model);
    }

    /**
     * lamnt98
     * 27/06
     * catch request from client to delete account
     * @param model
     * @return
     */
    @DeleteMapping("/deleteaccount")
    public MessageDTO login(@RequestBody DeleteAccountRequestDto model)
    {
        return deleteAccountService.deleteAccount(model);
    }


    /**
     * kimpt142
     * 27/6/2020
     * catch request from client to update new password
     * @param model include a user list and new password
     * @return MessageDTO with (1,success) if success
     */
    @PostMapping("/resetpassword")
    public MessageDTO login(@RequestBody ResetPassRequestDTO model)
    {
        return resetPassService.resetMultiplePassword(model.getUserNameList(), model.getPassWord());
    }

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

    @PostMapping("/userlist")
    public SearchUserResponseDto getRoleList(@RequestBody SearchUserRequestDto model)
    {
        SearchUserResponseDto responseDto = new SearchUserResponseDto();
        responseDto = searchUserService.searchUser(model);
        return  responseDto;
    }
}