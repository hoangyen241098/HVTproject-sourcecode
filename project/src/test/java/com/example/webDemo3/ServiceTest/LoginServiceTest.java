package com.example.webDemo3.ServiceTest;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageAccountResponseDto.LoginResponseDto;
import com.example.webDemo3.dto.request.manageAccountRequestDto.LoginRequestDto;
import com.example.webDemo3.entity.Role;
import com.example.webDemo3.entity.SchoolYear;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.SchoolYearRepository;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.*;
import com.example.webDemo3.service.manageAccountService.LoginService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/*
kimpt142 - 12/07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private SchoolYearRepository schoolYearRepository;

    /**
     * kimpt142 - 12/07
     * create mockup for user repository
     */
    @Before
    public void setUpUserRepository() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("123");
        user1.setRole(new Role(0));

        SchoolYear schoolCurrent = new SchoolYear();
        schoolCurrent.setYearID(1);
        Mockito.when(userRepository.findUserByUsername("user1")).thenReturn(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("123");
        user2.setRole(new Role(1));
        user2.setStatus(1);
        Mockito.when(userRepository.findUserByUsername("user2")).thenReturn(user2);
    }

    /**
     * kimpt142 - 12/07
     * test login successfully
     */
    @Test
    public void testLoginSuccess() {
        LoginResponseDto responseDto = new LoginResponseDto();
        MessageDTO message = new MessageDTO();
        message = Constant.SUCCESS;
        responseDto.setRoleid(0);
        responseDto.setMessage(message);
        responseDto.setCurrentYearId(2);

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername("user1");
        requestDto.setPassword("123");

        Assert.assertEquals(responseDto, loginService.checkLoginUser(requestDto));
    }

    /**
     * kimpt142 - 12/07
     * test login fail when account is inactive
     */
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

    /**
     * kimpt142 - 12/07
     * test login fail when input wrong password
     */
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

    /**
     * kimpt142 - 12/07
     * test login fail when username not exist
     */
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
