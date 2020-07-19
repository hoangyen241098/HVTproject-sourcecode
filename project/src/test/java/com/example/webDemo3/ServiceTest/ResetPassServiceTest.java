package com.example.webDemo3.ServiceTest;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.entity.Role;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.manageAccountService.ResetPassService;
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
public class ResetPassServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ResetPassService resetPassService;

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
     * Test reset multple password successfully
     */
    @Test
    public void testResetPassSuccess() {
        MessageDTO messageResponse = new MessageDTO();
        messageResponse = Constant.RESET_PASS_SUCCESS;

        String[] userNameList = {"user1"};
        String passWord = "1q2312";

        Assert.assertEquals(messageResponse, resetPassService.resetMultiplePassword(userNameList,passWord));
    }

    /**
     * kimpt142 - 12/07
     * Test reset multple password fail
     */
    @Test
    public void testResetPassFail() {
        MessageDTO messageResponse = new MessageDTO();
        messageResponse = Constant.USER_NOT_EXIT;

        String[] userNameList = {"user1", "user3"};
        String passWord = "1q2312";

        Assert.assertEquals(messageResponse, resetPassService.resetMultiplePassword(userNameList,passWord));
    }
}
