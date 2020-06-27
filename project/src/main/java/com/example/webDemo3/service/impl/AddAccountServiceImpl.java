package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddAccResquestDTO;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.Role;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.AddAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddAccountServiceImpl implements AddAccountService {

    @Autowired
    private UserRepository userRepository;

    /**
     * kimpt142
     * 27/6
     * add account to user tables
     * @param resquestDTO include username, password, roleid, classid, phone, email
     * @return message success or fail
     */
    @Override
    public MessageDTO addAccount(AddAccResquestDTO resquestDTO) {
        User newUser = new User();
        String userName = resquestDTO.getUserName();
        Integer roleId = resquestDTO.getRoleId();
        Integer classId = resquestDTO.getClassId();
        MessageDTO message = new MessageDTO();

        List<String> userList = userRepository.findAllUsername();
        //check username existed in user table
        if(!userList.contains(userName))
        {
            newUser.setUsername(userName);
            newUser.setPassword(resquestDTO.getPassWord());
            newUser.setName(resquestDTO.getFullName());
            newUser.setPhone(resquestDTO.getPhone());
            newUser.setEmail(resquestDTO.getEmail());
            if(roleId != null){
                newUser.setRole(new Role(roleId));
            }

            if(classId != null){
                newUser.setClassSchool(new Class(classId));
            }

            userRepository.save(newUser);
            message = Constant.SUCCESS;
            return message;
        }
        message = Constant.USERNAME_EXIST;
        return message;
    }
}
