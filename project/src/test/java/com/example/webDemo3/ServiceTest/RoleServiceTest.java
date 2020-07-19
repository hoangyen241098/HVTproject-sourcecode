package com.example.webDemo3.ServiceTest;

import com.example.webDemo3.entity.Role;
import com.example.webDemo3.repository.RoleRepository;
import com.example.webDemo3.service.manageClassService.RoleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/*
kimpt142 - 12/07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceTest {

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    /**
     * kimpt142 - 12/07
     * create mockup for role repository
     */
    @Before
    public void setUpRoleRepository() {
        Role role1 = new Role(1);
        Role role2 = new Role(2);
        List<Role> roleList = new ArrayList<>();
        roleList.add(role1);
        roleList.add(role2);
        Mockito.when(roleRepository.findAll()).thenReturn(roleList);
    }

    /**
     * kimpt142 - 12/07
     * Test get role list
     */
    @Test
    public void testGetAllRoleListSuccess() {
        Integer actual =  roleService.getAllRole().getMessage().getMessageCode();
        Integer expected = 0;
        Assert.assertEquals(expected, actual);
    }
}
