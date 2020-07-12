package com.example.webDemo3.dto;

import com.example.webDemo3.entity.User;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * kimpt142 - 28/6
 */
@Data
public class SearchUserResponseDto {
    private Page<User> userList;
    private MessageDTO message;
}
