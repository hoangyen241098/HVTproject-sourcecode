package com.example.webDemo3.service.impl;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.ChangePasswordRequestDto;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordmpl implements ChangePasswordService {

    @Autowired
    private UserRepository userRepository;

    /**
     * lamnt98
     * 23/06
     * check password and update new password
     * @param user
     * @return MessageDTO
     */
    @Override
    public MessageDTO checkChangePasswordUser(ChangePasswordRequestDto user) {
        MessageDTO message = new MessageDTO();
        User newUser = null;

        /**
         * find user in database
         */
        try {
            newUser = userRepository.findUserByUsername(user.getUsername());
        }
        catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }

        /**
         * check oldpassword and update newpassword
         */
        if(!newUser.getPassword().equals(user.getOldpassword())){
            message.setMessageCode(1);
            message.setMessage("Mật khẩu không đúng.");
        }
        else{
            newUser.setPassword(user.getNewpassword());
            userRepository.save(newUser);
            message.setMessageCode(0);
            message.setMessage("Mật khẩu đã được đặt lại!");
        }
        return message;
    }
}
