package com.example.webDemo3.ServiceTest;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddAccResquestDTO;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.AddAccountService;
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

/**
 * lamnt98
 * 12/07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AddAccountServiceTest {
    @MockBean
    UserRepository userRepository;

    @Autowired
    private AddAccountService addAccountService;

    @Before
    public void setUp() {

        List<String> userList = new ArrayList<>();
        userList.add("user1");
        userList.add("user2");
        userList.add("user3");

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("123");

        Mockito.when(userRepository.findAllUsername()).thenReturn(userList);
    }

    /**
     * Test username exits in Add Account Service
     * @throws Exception
     */
    @Test
    public void testUserNameExists() {
        MessageDTO message = new MessageDTO();
        message = Constant.USERNAME_EXIST;

        AddAccResquestDTO requestDto = new AddAccResquestDTO();
        requestDto.setUserName("user1");
        requestDto.setPassWord("123");
        requestDto.setRoleId(1);
        requestDto.setClassId(1);

        Assert.assertEquals(message, addAccountService.addAccount(requestDto));
    }

    /**
     * Test username empty in Add Account Service
     * @throws Exception
     */
    @Test
    public void testUserNameEmpty() {
        MessageDTO message = new MessageDTO();
        message = Constant.USERNAME_EMPTY;

        AddAccResquestDTO requestDto = new AddAccResquestDTO();
        requestDto.setUserName("");
        requestDto.setPassWord("123");
        requestDto.setRoleId(1);
        requestDto.setClassId(1);

        Assert.assertEquals(message, addAccountService.addAccount(requestDto));
    }

    /**
     * Test password empty in Add Account Service
     * @throws Exception
     */
    @Test
    public void testPasswordEmpty() {
        MessageDTO message = new MessageDTO();
        message = Constant.PASSWORD_EMPTY;

        AddAccResquestDTO requestDto = new AddAccResquestDTO();
        requestDto.setUserName("user1234");
        requestDto.setPassWord("");
        requestDto.setRoleId(1);
        requestDto.setClassId(1);

        Assert.assertEquals(message, addAccountService.addAccount(requestDto));
    }

    /**
     * Test  Add Account Service successfully
     * @throws Exception
     */
    @Test
    public void testAddAccountSuccess() {
        MessageDTO message = new MessageDTO();
        message = Constant.SUCCESS;

        AddAccResquestDTO requestDto = new AddAccResquestDTO();
        requestDto.setUserName("user1234");
        requestDto.setPassWord("123");
        requestDto.setRoleId(1);
        requestDto.setClassId(1);
        requestDto.setFullName("");
        requestDto.setPhone("");
        requestDto.setEmail("");

        Assert.assertEquals(message, addAccountService.addAccount(requestDto));
    }
}
