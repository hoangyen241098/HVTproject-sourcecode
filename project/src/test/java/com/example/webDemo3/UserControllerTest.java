package com.example.webDemo3;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.controller.UserController;
import com.example.webDemo3.dto.LoginResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.LoginRequestDto;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
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
                .contentType(MediaType.APPLICATION_JSON))// Thực hiện GET REQUEST
                .andExpect(status().isOk()) // Mong muốn Server trả về status 200
                .andExpect( jsonPath("$.message.messageCode", is(0)));
    }
}
