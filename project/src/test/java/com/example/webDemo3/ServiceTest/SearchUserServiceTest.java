package com.example.webDemo3.ServiceTest;

import com.example.webDemo3.dto.request.manageAccountRequestDto.SearchUserRequestDto;
import com.example.webDemo3.entity.Role;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.manageAccountService.SearchUserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/*
kimpt142 - 12/07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchUserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private SearchUserService searchUserService;

    /**
     * kimpt142 - 12/07
     * create mockup for user repository
     */
    @Before
    public void setUpUserRepository() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("123");
        user1.setRole(new Role(1));

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("123");
        user2.setRole(new Role(1));
        user2.setStatus(0);

        Pageable pagingSortDesc = PageRequest.of(0, 10, Sort.by("username").descending());
        Pageable pagingSortAsc = PageRequest.of(0, 10, Sort.by("name").ascending());

        List<User> userListForConditionSuccess = new ArrayList<>();
        userListForConditionSuccess.add(user1);
        userListForConditionSuccess.add(user2);
        Page<User> pagedResponse = new PageImpl(userListForConditionSuccess);

        Mockito.when(userRepository.searchUserByCondition("user",1, pagingSortDesc )).thenReturn(pagedResponse);
        Mockito.when(userRepository.searchUserByUsername("user", pagingSortAsc )).thenReturn(pagedResponse);

    }

    /**
     * kimpt142 - 12/07
     * test search successfully with username, roleid, sort and order
     */
    @Test
    public void testSearchUserSuccess1() {
        SearchUserRequestDto requestModel = new SearchUserRequestDto();
        requestModel.setPageNumber(0);
        requestModel.setUserName("user");
        requestModel.setRoleId(1);
        requestModel.setOrderBy(0);
        requestModel.setSortBy(0);

        long actual = searchUserService.searchUser(requestModel).getUserList().getTotalElements();
        long expected = 2;
        Assert.assertEquals(expected, actual);
    }

    /**
     * kimpt142 - 12/07
     * test search successfully with username
     */
    @Test
    public void testSearchUserSuccess2() {
        SearchUserRequestDto requestModel = new SearchUserRequestDto();
        requestModel.setPageNumber(0);
        requestModel.setUserName("user");
        requestModel.setOrderBy(1);
        requestModel.setSortBy(1);

        long actual = searchUserService.searchUser(requestModel).getUserList().getTotalElements();
        long expected = 2;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSearchUserFail() {
        SearchUserRequestDto requestModel = new SearchUserRequestDto();
        requestModel.setPageNumber(0);
        requestModel.setUserName("user");
        requestModel.setOrderBy(2);
        requestModel.setSortBy(1);

        Page<User> actual = searchUserService.searchUser(requestModel).getUserList();
        Page<User> expected = null;
        Assert.assertEquals(expected, actual);
    }
}
