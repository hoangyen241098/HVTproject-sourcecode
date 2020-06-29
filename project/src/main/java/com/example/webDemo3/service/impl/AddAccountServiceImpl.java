package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddAccResquestDTO;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.Role;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.AddAccountService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        String passWord = resquestDTO.getPassWord();
        Integer roleId = resquestDTO.getRoleId();
        Integer classId = resquestDTO.getClassId();
        MessageDTO message = new MessageDTO();

        List<String> userList = userRepository.findAllUsername();
        //check username existed in user table
        if(!userList.contains(userName))
        {
            if(!userName.trim().equals("")) {
                newUser.setUsername(userName);
            }
            else {
                message = Constant.USERNAME_EMPTY;
                return message;
            }

            if(!passWord.trim().equals("")) {
                newUser.setPassword(passWord);
            }
            else {
                message = Constant.PASSWORD_EMPTY;
                return message;
            }

            newUser.setName(resquestDTO.getFullName());
            newUser.setPhone(resquestDTO.getPhone());
            newUser.setEmail(resquestDTO.getEmail());

            if(roleId != null){
                newUser.setRole(new Role(roleId));
            }

            //if(classId != null){
                //newUser.setClassSchool(new Class(classId));
            //}

            try {
                userRepository.save(newUser);
            }
            catch (Exception e)
            {
                message.setMessageCode(1);
                message.setMessage(e.toString());
                return message;
            }

            message = Constant.SUCCESS;
            return message;
        }
        message = Constant.USERNAME_EXIST;
        return message;
    }
}
