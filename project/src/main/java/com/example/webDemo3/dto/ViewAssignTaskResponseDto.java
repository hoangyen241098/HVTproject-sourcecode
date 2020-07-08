package com.example.webDemo3.dto;

import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.User;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.util.List;

/**
 * lamnt98
 * 07/07
 */
@Data
public class ViewAssignTaskResponseDto {
    List<ClassRedStarResponseDto> listAssignTask;
    List<User> listRedStar;
    List<Class> listClass;
    List<Date> listDate;
    private MessageDTO message;
}
