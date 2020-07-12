package com.example.webDemo3.controller;

import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.*;
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

    @Autowired
    private RoleService roleService;

    @Autowired
    private ClassService classService;

    @Autowired
    private GenerateAccountService generateAccountService;

    /**
     * kimpt142
     * 27/6/2020
     * catch request from client to add new account
     * @param model include all property of user table
     * @return MessageDTO with (1,success) if success
     */
    @PostMapping("/createaccount")
    public MessageDTO createAccount(@RequestBody AddAccResquestDTO model)
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
    @PostMapping("/deleteaccount")
    public MessageDTO deleteAccount(@RequestBody DeleteAccountRequestDto model)
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
    public MessageDTO resetPassword(@RequestBody ResetPassRequestDTO model)
    {
        return resetPassService.resetMultiplePassword(model.getUserNameList(), model.getPassWord());
    }

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
        classDto = classService.getClassList();
        return  classDto;
    }

    /**
     * kimpt142
     * 28/6
     * catch request to get user list
     * @return reponseDTO with a user list and messagedto
     */
    @PostMapping("/userlist")
    public SearchUserResponseDto getUserList(@RequestBody SearchUserRequestDto model)
    {
        SearchUserResponseDto responseDto = new SearchUserResponseDto();
        responseDto = searchUserService.searchUser(model);
        return  responseDto;
    }

    /**
     * kimpt142
     * 28/6
     * catch request to generate account name
     * @return reponseDTO with an account name and messagedto
     */
    @PostMapping("/genaccname")
    public GenerateNameResponseDto getUserList(@RequestBody GenerateNameRequestDto model)
    {
        GenerateNameResponseDto responseDto = new GenerateNameResponseDto();
        responseDto = generateAccountService.generateAccountName(model);
        return  responseDto;
    }
}
