package com.example.webDemo3.dto;

import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.SchoolWeek;
import com.example.webDemo3.entity.SchoolYear;
import lombok.Data;

import java.util.List;

/**
 * lamnt98
 * 01/07
 */
@Data
public class ListYearAndClassResponseDto extends ListYearAndWeekResponseDto {
    private List<Class> classList;
}
