package com.example.webDemo3.controller.testcontroller;

import com.example.webDemo3.entity.testentity.User;
import com.example.webDemo3.repository.testrepository.UserRepository;
import com.example.webDemo3.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private DemoService service;


    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/") // Nếu người dùng request tới địa chỉ "/"
    public String directLoginPage(Model model) {
        return "login"; // Trả về file index.html
    }

    /**
     *
     * @param model
     * @return
     */
    @PostMapping("/login") // Nếu người dùng request tới địa chỉ "/"
    public String getLogin(@RequestBody User model) {
        if(service.checkLogin(model.getUsername(), model.getPassword())){
            return "sucessLogin";
        }
        return "failLogin";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/success") // Nếu người dùng request tới địa chỉ "/"
    public String index(Model model) {
        model.addAttribute("userList",service.getAllList());
        return "index"; // Trả về file index.html
    }


    /**
     *
     * @param model
     * @return
     */
    @PostMapping("/insert") // Nếu người dùng request tới địa chỉ "/"
    public User index2(@RequestBody User model) {//@RequestBody Nhan gia tri va mapping voi User
        //model.addAttribute("userList",userRepository.findAll());
        User user = service.save(model);
        return user;
    }

    /**
     *
     * @param model
     * @param id
     * @return
     */
    @PutMapping("/update/{id}") // Nếu người dùng request tới địa chỉ "/" Cap nhat du lieu
    public User index3(@RequestBody User model, @PathVariable("id") long id) {//@RequestBody Nhan gia tri va mapping voi User
        //model.addAttribute("userList",userRepository.findAll());
        User user = service.update(model);
        return user;
    }

    /**
     *
     * @param ids
     */
    @DeleteMapping("/delete")
    public void index4(@RequestBody String[] ids) {//@RequestBody Nhan gia tri va mapping voi User
        //model.addAttribute("userList",userRepository.findAll());
        service.delete(ids);
    }
    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }

}