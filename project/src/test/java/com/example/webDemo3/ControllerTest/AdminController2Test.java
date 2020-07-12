package com.example.webDemo3.ControllerTest;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.controller.AdminController;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddAccResquestDTO;
import com.example.webDemo3.dto.request.DeleteAccountRequestDto;
import com.example.webDemo3.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * lamnt98
 * 12/07
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminController2Test {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddAccountService addAccountService;

    @MockBean
    private SearchUserService searchUserService;

    @MockBean
    private DeleteAccountService deleteAccountService;

    @MockBean
    private ResetPassService resetPassService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private ClassService classService;

    @MockBean
    private GenerateAccountService generateAccountService;

    /**
     * Test add account controller
     * @throws Exception
     */
    @Test
    public void testAddAccount() throws Exception {

        AddAccResquestDTO requestDto = new AddAccResquestDTO();
        requestDto.setUserName("user1234");
        requestDto.setPassWord("123");
        requestDto.setRoleId(1);
        requestDto.setClassId(1);
        requestDto.setFullName("");
        requestDto.setPhone("");
        requestDto.setEmail("");

        MessageDTO message = Constant.SUCCESS;

        String content = "{\"userName\":\"user1234\",\"passWord\":\"123\",\"fullName\":\"\",\"roleId\":\"1\",\"phone\":\"\",\"email\":\"\",\"classId\":\"1\"}";
        given(addAccountService.addAccount(requestDto)).willReturn(message);

        mvc.perform(post("/api/admin/createaccount").content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.messageCode", is(0)));
    }

    /**
     * Test delete account controller
     * @throws Exception
     */
    @Test
    public void deleteAccount() throws Exception {

        DeleteAccountRequestDto requestDto = new DeleteAccountRequestDto();
        List<String> listUser = new ArrayList<>();
        listUser.add("user1");
        listUser.add("user3");
        requestDto.setListUser(listUser);

        MessageDTO message = Constant.DELETE_ACCOUNT_SUCCESS;

        String content = "{\"listUser\": [\"user1\",\"user3\"]}";
        given(deleteAccountService.deleteAccount(requestDto)).willReturn(message);

        mvc.perform(post("/api/admin/deleteaccount").content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.messageCode", is(0)));
    }
}
