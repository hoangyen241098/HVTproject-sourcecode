package com.example.webDemo3.service;

import com.example.webDemo3.dto.MessageDTO;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.sql.Date;

public interface AddTimeTableService {

    public MessageDTO addTimetable(HSSFWorkbook workbook, Date applyDate);
}
