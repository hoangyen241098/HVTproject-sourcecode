package com.example.webDemo3.ControllerTest;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.controller.UserController;
import com.example.webDemo3.dto.LoginResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.ViewPerInforResponseDto;
import com.example.webDemo3.dto.request.ChangePasswordRequestDto;
import com.example.webDemo3.dto.request.EditPerInforRequestDto;
import com.example.webDemo3.dto.request.LoginRequestDto;
import com.example.webDemo3.dto.request.ViewPerInforRequestDto;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.ChangePasswordService;
import com.example.webDemo3.service.EditPerInforService;
import com.example.webDemo3.service.LoginService;
import com.example.webDemo3.service.ViewPerInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.servlet.FlashMap;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    /**
     * Đối tượng MockMvc do Spring cung cấp
     * Có tác dụng giả lập request, thay thế việc khởi động Server
     */
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LoginService loginService;

    @MockBean
    private ChangePasswordService changePasswordService;

    @MockBean
    private ViewPerInfoService viewPerInfoService;

    @MockBean
    private EditPerInforService editPerInforService;

    @Test
    public void testLogin() throws Exception {

        MessageDTO messageDTO = Constant.SUCCESS;
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setRoleid(1);
        loginResponseDto.setMessage(messageDTO);

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("user1");
        loginRequestDto.setPassword("123");

        String content = "{\"username\":\"user1\",\"password\":\"123\"}";
        given(loginService.checkLoginUser(loginRequestDto)).willReturn(loginResponseDto);

        mvc.perform(post("/api/user/login").content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.message.messageCode", is(0)));
    }

    /**
     * Test view prosonal information controller
     * @throws Exception
     */
    @Test
    public void testViewPersonalInformation() throws Exception {

        ViewPerInforRequestDto requestDto = new ViewPerInforRequestDto();
        requestDto.setUserName("user1");

        MessageDTO message = Constant.SUCCESS;
        ViewPerInforResponseDto responseDto = new ViewPerInforResponseDto();
        responseDto.setUserName("user1");
        responseDto.setRoleName("Admin");
        responseDto.setPhone("0120356662");
        responseDto.setEmail("lamntse05790@fpt.edu.vn");
        responseDto.setClassName("10V");
        responseDto.setMessage(message);

        String content = "{\"userName\":\"user1\"}";
        given(viewPerInfoService.getUserInformation(requestDto)).willReturn(responseDto);

        mvc.perform(post("/api/user/viewinformation").content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.message.messageCode", is(0)));
    }

    /**
     * Test change password controller
     * @throws Exception
     */
    @Test
    public void testChangePassword() throws Exception {

        ChangePasswordRequestDto requestDto = new ChangePasswordRequestDto();
        requestDto.setUserName("user1");
        requestDto.setOldPassword("123");
        requestDto.setNewPassword("123456");

        MessageDTO message = Constant.SUCCESS;

        String content = "{\"userName\":\"user1\",\"oldPassword\":\"123\",\"newPassword\":\"123456\"}";
        given(changePasswordService.checkChangePasswordUser(requestDto)).willReturn(message);

        mvc.perform(post("/api/user/changepassword").content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.messageCode", is(0)));
    }
    @Test
    public void testLogoutSuccess() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("username", "user1");

        mvc.perform(post("/api/user/logout").session(session)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.messageCode", is(0)));
    }


    /**
     * Test edit personal information controller
     * @throws Exception
     */
    @Test
    public void testEditInformation() throws Exception {

        EditPerInforRequestDto requestDto = new EditPerInforRequestDto();
        requestDto.setUserName("user1");
        requestDto.setFullName("Lam");
        requestDto.setPhone("0120356662");
        requestDto.setEmail("lamntse05790@fpt.edu.vn");

        MessageDTO message = Constant.EDIT_INFOR_SUCCESS;

        String content = "{\"userName\":\"user1\",\"fullName\":\"Lam\",\"phone\":\"0120356662\",\"email\":\"lamntse05790@fpt.edu.vn\"}";
        given(editPerInforService.editUserInformation(requestDto)).willReturn(message);

        mvc.perform(post("/api/user/editinformation").content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageCode", is(0)));
    }
    @Test
    public void testLogoutFail() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("username", null);
        mvc.perform(post("/api/user/logout").session(session)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
