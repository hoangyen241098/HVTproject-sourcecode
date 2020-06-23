package com.example.webDemo3.controller.testcontroller;

import com.example.webDemo3.entity.testentity.User;
import com.example.webDemo3.repository.testrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/") // Nếu người dùng request tới địa chỉ "/"
    public String index(Model model) {
        model.addAttribute("userList",userRepository.findAll());
        return "index"; // Trả về file index.html
    }

    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }

}