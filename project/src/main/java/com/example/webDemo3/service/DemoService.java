package com.example.webDemo3.service;

import com.example.webDemo3.entity.testentity.User;

import java.util.List;

public interface DemoService {
    User save(User user);
    List<User> getAllList();
    User update(User user);
    void delete(String[] userName);
    boolean checkLogin(String userName, String password);
}
