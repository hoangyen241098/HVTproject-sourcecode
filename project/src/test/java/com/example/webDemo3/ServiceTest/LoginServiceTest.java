package com.example.webDemo3.ServiceTest;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.LoginResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.LoginRequestDto;
import com.example.webDemo3.entity.Role;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.LoginService;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {
    @MockBean
    UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    @Before
    public void setUp() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("123");
        user1.setRole(new Role(0));
        Mockito.when(userRepository.findUserByUsername("user1")).thenReturn(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("123");
        user2.setRole(new Role(1));
        user2.setStatus(1);
        Mockito.when(userRepository.findUserByUsername("user2")).thenReturn(user2);
    }

    @Test
    public void testLoginSuccess() {
        LoginResponseDto responseDto = new LoginResponseDto();
        MessageDTO message = new MessageDTO();
        message = Constant.SUCCESS;
        responseDto.setRoleid(0);
        responseDto.setMessage(message);

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername("user1");
        requestDto.setPassword("123");

        Assert.assertEquals(responseDto, loginService.checkLoginUser(requestDto));
    }

    @Test
    public void testLoginInactiveFail() {
        LoginResponseDto responseDto = new LoginResponseDto();
        MessageDTO message = new MessageDTO();
        message = Constant.USER_INACTIVE;
        responseDto.setRoleid(null);
        responseDto.setMessage(message);

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername("user2");
        requestDto.setPassword("123");

        Assert.assertEquals(responseDto, loginService.checkLoginUser(requestDto));
    }

    @Test
    public void testLoginWrongPass() {
        LoginResponseDto responseDto = new LoginResponseDto();
        MessageDTO message = new MessageDTO();
        message = Constant.WRONG_PASSWORD;
        responseDto.setRoleid(null);
        responseDto.setMessage(message);

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername("user1");
        requestDto.setPassword("1234");

        Assert.assertEquals(responseDto, loginService.checkLoginUser(requestDto));
    }

    @Test
    public void testLoginUserNotExist() {
        LoginResponseDto responseDto = new LoginResponseDto();
        MessageDTO message = new MessageDTO();
        message = Constant.USER_NOT_EXIT;
        responseDto.setRoleid(null);
        responseDto.setMessage(message);

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername("user3");
        requestDto.setPassword("1234");

        Assert.assertEquals(responseDto, loginService.checkLoginUser(requestDto));
    }
}
