package com.example.webDemo3.controller;

import com.example.webDemo3.dto.ListYearAndWeekResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.ListWeekRequestDto;
import com.example.webDemo3.service.AddTimeTableService;
import com.example.webDemo3.service.TimeTableService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

// actor: yenvb - update timetable
@Controller
public class TimeTableController {

    @Autowired
    private AddTimeTableService addTimeTableService;

//    @Autowired
//    private TimeTableService viewTimTaClassService;

    @GetMapping("/manageTimetable")
    public String index(Model model) {
//        ListYearAndWeekResponseDto list = viewTimTaClassService.getListYearAndListWeek();
//        model.addAttribute("listWeek",list.getListWeek());
        return "manageTimetable";
    }

    @PostMapping("/manageTimetable")
    public void mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile, Model model)
    {
        MessageDTO message = new MessageDTO();
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(reapExcelDataFile.getInputStream());
        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage("không đúng định dạng file");
            System.out.println(e);
        }
        if(workbook != null){
            Date date = Date.valueOf("2020-01-01");
            message = addTimeTableService.addTimetable(workbook,date);
        }
        model.addAttribute("message",message.getMessage());
    }


}
