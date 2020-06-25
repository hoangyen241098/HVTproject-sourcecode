package com.example.webDemo3.service.impl;

import com.example.webDemo3.entity.Role;
import com.example.webDemo3.repository.RoleRepository;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * kimpt142
     * 25/6
     * @return
     */
    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}
