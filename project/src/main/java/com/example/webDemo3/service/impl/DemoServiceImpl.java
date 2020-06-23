package com.example.webDemo3.service.impl;

import com.example.webDemo3.entity.testentity.User;
import com.example.webDemo3.repository.testrepository.UserRepository;
import com.example.webDemo3.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {
        User new1 = userRepository.save(user);
        return new1;
    }

    @Override
    public List<User> getAllList() {
        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        User oldUser = userRepository.findById(user.getUsername()).orElse(null);
        oldUser.setPassword(user.getPassword());
        oldUser.setName(user.getName());
        oldUser = userRepository.save(oldUser);
        return oldUser;
    }

    @Override
    public void delete(String[] userName) {
        for(String u:userName){
            userRepository.deleteById(u);
        }
    }

    @Override
    public boolean checkLogin(String userName, String password) {
        User user = userRepository.findById(userName).orElse(null);
        if(user != null && user.getUsername().equals(userName) && user.getPassword().equals(password) ){
            return true;
        }
        return false;
    }
}
