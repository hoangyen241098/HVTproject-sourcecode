package com.example.webDemo3.controller.testcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
//    @Autowired
//    private UserRepository u;
//
//    @Autowired
//    private TodoRepository todoRepository;
    // Đón nhận request GET
    @GetMapping("/") // Nếu người dùng request tới địa chỉ "/"
    public String index(Model model) {
//        List<Users> k = u.findAll() ;
//        model.addAttribute("userList", k) ;
        return "index"; // Trả về file index.html
    }

}