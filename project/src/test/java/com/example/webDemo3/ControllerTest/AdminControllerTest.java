package com.example.webDemo3.ControllerTest;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.controller.AdminController;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.RoleListResponseDTO;
import com.example.webDemo3.dto.SearchUserResponseDto;
import com.example.webDemo3.dto.request.AddAccResquestDTO;
import com.example.webDemo3.dto.request.DeleteAccountRequestDto;
import com.example.webDemo3.dto.request.SearchUserRequestDto;
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

/*
kimpt142 - 12/07
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

    @Test
    public void testResetPassWord() throws Exception {

        MessageDTO messageDTO = Constant.SUCCESS;

        String[] userNameList = {"user1", "user2"};
        String passWord = "hehe";

        String content = "{\"userNameList\": [\"user1\", \"user2\"],\"passWord\" : \"hehe\"}";
        given(resetPassService.resetMultiplePassword(userNameList, passWord)).willReturn(messageDTO);

        mockMvc.perform(post("/api/admin/resetpassword").content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.messageCode", is(0)));
    }

    /**
     * kimpt142 - 12/07
     * Test search user by multiple condition
     */
    @Test
    public void testSearchUser() throws Exception {
        SearchUserRequestDto requestDto = new SearchUserRequestDto();
        requestDto.setPageNumber(0);
        requestDto.setUserName("user2");
        requestDto.setOrderBy(1);
        requestDto.setSortBy(0);
        requestDto.setRoleId(1);

        SearchUserResponseDto responseDto = new SearchUserResponseDto();
        MessageDTO messageDTO = Constant.SUCCESS;
        responseDto.setMessage(messageDTO);

        String content = "{\"userName\" : \"user2\",\"roleId\" : \"1\",\"sortBy\": \"0\",\"orderBy\" : \"1\",\"pageNumber\" : \"0\"}";
        given(searchUserService.searchUser(requestDto)).willReturn(responseDto);

        mockMvc.perform(post("/api/admin/userlist").content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.message.messageCode", is(0)));
    }

    /**
     * kimpt142 - 12/07
     * Test search user by multiple condition
     */
    @Test
    public void testGetRoleList() throws Exception {

        RoleListResponseDTO responseDto = new RoleListResponseDTO();
        MessageDTO messageDTO = Constant.SUCCESS;
        responseDto.setMessage(messageDTO);

        given(roleService.getAllRole()).willReturn(responseDto);

        mockMvc.perform(post("/api/admin/rolelist")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.message.messageCode", is(0)));
    }

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

        mockMvc.perform(post("/api/admin/createaccount").content(content)
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

        mockMvc.perform(post("/api/admin/deleteaccount").content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.messageCode", is(0)));
    }
}
