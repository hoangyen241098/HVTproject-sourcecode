package com.example.webDemo3.controller.testcontroller;

import com.example.webDemo3.entity.testentity.User;
import com.example.webDemo3.repository.testrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @PostMapping("/test")
    public User findByid(@RequestBody User user) {
        User t = userRepository.findUserByUsername(user.getUsername());
        return t;
    }

}
