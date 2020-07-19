package com.example.webDemo3.ServiceTest;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.manageAccountRequestDto.ChangePasswordRequestDto;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.manageAccountService.ChangePasswordService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * lamnt98
 * 12/07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChangePasswordServiceTest {
    @MockBean
    UserRepository userRepository;

    @Autowired
    private ChangePasswordService changePasswordService;

    @Before
    public void setUp() {

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("123");

        Mockito.when(userRepository.findUserByUsername("user1")).thenReturn(user1);
    }

    /**
     * Test in change password service successfully
     * @throws Exception
     */
    @Test
    public void changePasswordSuccess() {
        MessageDTO message = new MessageDTO();
        message = Constant.CHANGE_PASS_SUCCESS;

        ChangePasswordRequestDto requestDto = new ChangePasswordRequestDto();
        requestDto.setUserName("user1");
        requestDto.setOldPassword("123");
        requestDto.setNewPassword("123456");

        Assert.assertEquals(message, changePasswordService.checkChangePasswordUser(requestDto));
    }

    /**
     * Test usernam empty in change password service
     * @throws Exception
     */
    @Test
    public void testUserNameEmpty() {
        MessageDTO message = new MessageDTO();
        message = Constant.USERNAME_EMPTY;

        ChangePasswordRequestDto requestDto = new ChangePasswordRequestDto();
        requestDto.setUserName("");
        requestDto.setOldPassword("123");
        requestDto.setNewPassword("123456");

        Assert.assertEquals(message, changePasswordService.checkChangePasswordUser(requestDto));
    }

    /**
     * Test password empty in change password service
     * @throws Exception
     */
    @Test
    public void testPasswordEmpty() {
        MessageDTO message = new MessageDTO();
        message = Constant.PASSWORD_EMPTY;

        ChangePasswordRequestDto requestDto = new ChangePasswordRequestDto();
        requestDto.setUserName("user1");
        requestDto.setOldPassword("");
        requestDto.setNewPassword("");

        Assert.assertEquals(message, changePasswordService.checkChangePasswordUser(requestDto));
    }

    /**
     * Test user doesn't exist in change password service
     * @throws Exception
     */
    @Test
    public void testUserNotExists() {
        MessageDTO message = new MessageDTO();
        message = Constant.USER_NOT_EXIT;

        ChangePasswordRequestDto requestDto = new ChangePasswordRequestDto();
        requestDto.setUserName("user123");
        requestDto.setOldPassword("123");
        requestDto.setNewPassword("123456");

        Assert.assertEquals(message, changePasswordService.checkChangePasswordUser(requestDto));
    }

    /**
     * Test password wrong in change password service
     * @throws Exception
     */
    @Test
    public void testPasswordWrong() {
        MessageDTO message = new MessageDTO();
        message = Constant.WRONG_PASSWORD;

        ChangePasswordRequestDto requestDto = new ChangePasswordRequestDto();
        requestDto.setUserName("user1");
        requestDto.setOldPassword("12345");
        requestDto.setNewPassword("123456");

        Assert.assertEquals(message, changePasswordService.checkChangePasswordUser(requestDto));
    }

}
