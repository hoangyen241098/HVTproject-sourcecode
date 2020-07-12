package com.example.webDemo3.ServiceTest;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.EditPerInforRequestDto;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.GiftedClass;
import com.example.webDemo3.entity.Role;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.EditPerInforService;
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
 * 12-07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EditPerInforServiceTest {
    @MockBean
    UserRepository userRepository;

    @Autowired
    private EditPerInforService editPerInforService;

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

    /**
     * Test edit personal information service successfully
     * @throws
     */
    @Test
    public void editSuccess() {
        MessageDTO message = new MessageDTO();
        message = Constant.EDIT_INFOR_SUCCESS;

        EditPerInforRequestDto requestDto = new EditPerInforRequestDto();
        requestDto.setUserName("user1");
        requestDto.setFullName("Lam");
        requestDto.setPhone("0120356662");
        requestDto.setEmail("lamntse05790@fpt.edu.vn");

        Assert.assertEquals(message, editPerInforService.editUserInformation(requestDto));
    }

    /**
     * Test username empty in edit personal information service
     * @throws
     */
    @Test
    public void testUserNameEmpty() {
        MessageDTO message = new MessageDTO();
        message = Constant.USERNAME_EMPTY;

        EditPerInforRequestDto requestDto = new EditPerInforRequestDto();
        requestDto.setUserName("");
        requestDto.setFullName("Lam");
        requestDto.setPhone("0120356662");
        requestDto.setEmail("lamntse05790@fpt.edu.vn");

        Assert.assertEquals(message, editPerInforService.editUserInformation(requestDto));
    }

    /**
     * Test user doesn't exist in edit personal information service
     * @throws
     */
    @Test
    public void testUserNotExists() {
        MessageDTO message = new MessageDTO();
        message = Constant.USER_NOT_EXIT;

        EditPerInforRequestDto requestDto = new EditPerInforRequestDto();
        requestDto.setUserName("user123");

        Assert.assertEquals(message, editPerInforService.editUserInformation(requestDto));
    }
}
