package com.example.webDemo3.service.impl;

import com.example.webDemo3.dto.LoginDto;
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
    public LoginDto checkLoginUser(User u) {
        User user = userRepository.findUserByUsername(u.getUsername());
        LoginDto login = new LoginDto();
        if(user!=null && u.getPassword().equals(user.getPassword())){
            login.setMessageCode(1);
            login.setMessage("Success");
        }
        else{
            login.setMessageCode(0);
            login.setMessage("Fail");
        }
        return login;
    }
}
