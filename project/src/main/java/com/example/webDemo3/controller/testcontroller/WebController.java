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

}