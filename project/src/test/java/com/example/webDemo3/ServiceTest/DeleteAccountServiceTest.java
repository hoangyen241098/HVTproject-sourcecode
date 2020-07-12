package com.example.webDemo3.ServiceTest;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.DeleteAccountRequestDto;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.DeleteAccountService;
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
public class DeleteAccountServiceTest {
    @MockBean
    UserRepository userRepository;

    @Autowired
    private DeleteAccountService deleteAccountService;

    @Before
    public void setUp() {

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("123");
        user1.setStatus(0);

        Mockito.when(userRepository.findUserByUsername("user1")).thenReturn(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("123");
        user2.setStatus(1);

        User user3 = new User();
        user3.setUsername("user3");
        user3.setPassword("123");
        user3.setStatus(0);

        Mockito.when(userRepository.findUserByUsername("user2")).thenReturn(user2);
        Mockito.when(userRepository.findUserByUsername("user3")).thenReturn(user3);
    }

    /**
     * Test user doesn't exist in delete account service
     * @throws
     */
    @Test
    public void testUserNotExists() {
        MessageDTO message = new MessageDTO();
        message = Constant.USER_NOT_EXIT;

        DeleteAccountRequestDto requestDto = new DeleteAccountRequestDto();
        List<String> listUser = new ArrayList<>();
        listUser.add("user1");
        listUser.add("user2");
        requestDto.setListUser(listUser);

        Assert.assertEquals(message, deleteAccountService.deleteAccount(requestDto));
    }

    /**
     * Test delete account service successfully
     * @throws
     */
    @Test
    public void testDeleteSuccessful() {
        MessageDTO message = new MessageDTO();
        message = Constant.DELETE_ACCOUNT_SUCCESS;

        DeleteAccountRequestDto requestDto = new DeleteAccountRequestDto();
        List<String> listUser = new ArrayList<>();
        listUser.add("user1");
        listUser.add("user3");
        requestDto.setListUser(listUser);

        Assert.assertEquals(message, deleteAccountService.deleteAccount(requestDto));
    }
}
