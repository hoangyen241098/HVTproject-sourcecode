package com.example.webDemo3.controller.testcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/") // Nếu người dùng request tới địa chỉ "/"
    public String index(Model model) {
        return "index"; // Trả về file index.html
    }

    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model) {
        return "forgotPassword";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model) {
        return "changePassword";
    }

    @GetMapping("/manageAccount")
    public String manageAccount(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "manageAccount";
    }

}