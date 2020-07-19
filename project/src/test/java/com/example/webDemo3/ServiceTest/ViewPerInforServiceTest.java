package com.example.webDemo3.ServiceTest;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageAccountResponseDto.ViewPerInforResponseDto;
import com.example.webDemo3.dto.request.manageAccountRequestDto.ViewPerInforRequestDto;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.GiftedClass;
import com.example.webDemo3.entity.Role;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.manageAccountService.ViewPerInfoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewPerInforServiceTest {
    @MockBean
    UserRepository userRepository;

    @Autowired
    private ViewPerInfoService viewPerInfoService;


    @Before
    public void setUp() {
        Role role = new Role();
        role.setRoleId(1);
        role.setRoleName("Admin");

        GiftedClass giftedClass = new GiftedClass();
        giftedClass.setGiftedClassId(1);
        giftedClass.setName("V");

        Class newClass = new Class();
        newClass.setGiftedClass(giftedClass);
        newClass.setClassIdentifier("10T");
        newClass.setGrade(10);

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("123");
        user1.setRole(role);
        user1.setPhone("0120356662");
        user1.setEmail("lamntse05790@fpt.edu.vn");
        user1.setClassSchool(newClass);
        Mockito.when(userRepository.findUserByUsername("user1")).thenReturn(user1);
    }

    @Test
    public void testViewSuccess() {
        ViewPerInforResponseDto responseDto = new ViewPerInforResponseDto();
        MessageDTO message = new MessageDTO();
        responseDto.setUserName("user1");
        responseDto.setRoleName("Admin");
        responseDto.setPhone("0120356662");
        responseDto.setEmail("lamntse05790@fpt.edu.vn");
        responseDto.setClassName("10V");
        message = Constant.SUCCESS;
        responseDto.setMessage(message);

        ViewPerInforRequestDto requestDto = new ViewPerInforRequestDto();
        requestDto.setUserName("user1");

        Assert.assertEquals(responseDto, viewPerInfoService.getUserInformation(requestDto));
    }

    @Test
    public void testUserNameEmpty() {
        ViewPerInforResponseDto responseDto = new ViewPerInforResponseDto();
        MessageDTO message = new MessageDTO();
        message = Constant.USERNAME_EMPTY;
        responseDto.setMessage(message);

        ViewPerInforRequestDto requestDto = new ViewPerInforRequestDto();
        requestDto.setUserName("");

        Assert.assertEquals(responseDto, viewPerInfoService.getUserInformation(requestDto));
    }

    @Test
    public void testUserNotExists() {
        ViewPerInforResponseDto responseDto = new ViewPerInforResponseDto();
        MessageDTO message = new MessageDTO();
        message = Constant.USER_NOT_EXIT;
        responseDto.setMessage(message);

        ViewPerInforRequestDto requestDto = new ViewPerInforRequestDto();
        requestDto.setUserName("user123");

        Assert.assertEquals(responseDto, viewPerInfoService.getUserInformation(requestDto));
    }

}
