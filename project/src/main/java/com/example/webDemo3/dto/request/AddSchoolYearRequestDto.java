package com.example.webDemo3.dto.request;

import lombok.Data;
import java.sql.Date;

/*
kimpt142 - 06/07
 */
@Data
public class AddSchoolYearRequestDto {
    private Integer fromYear;
    private Integer toYear;
    private Date fromDate;
    private Date toDate;
}
