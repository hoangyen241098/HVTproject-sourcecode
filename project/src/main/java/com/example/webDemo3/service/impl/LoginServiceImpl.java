package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.LoginResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.LoginRequestDto;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    /**
     * kimpt142
     * 23/6/2020
     * check username and password exist
     * @param u include username and password
     * @return logindto(1,success) if success and (0,fail) is fail
     */
    @Override
    public LoginResponseDto checkLoginUser(LoginRequestDto u) {
        LoginResponseDto loginDto = new LoginResponseDto();
        MessageDTO message = new MessageDTO();
        User user = null;

        /**
         * Find username in database
         */
        try {
            user = userRepository.findUserByUsername(u.getUsername());
        }
        catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return loginDto;
        }

        /**
         * check username and password
         */
        if(user==null){
            message = Constant.USER_NOT_EXIT;
//            message.setMessageCode(1);
//            message.setMessage("Tên đăng nhập không tồn tại");
        }
        else if(!u.getPassword().equals(user.getPassword())){
            message.setMessageCode(1);
            message.setMessage("Mật khẩu không đúng.");
        }
        else{
            message.setMessageCode(0);
            message.setMessage("Thành công");
            loginDto.setRoleid(user.getRoleId());
        }

        loginDto.setMessage(message);
        return loginDto;
    }
}
