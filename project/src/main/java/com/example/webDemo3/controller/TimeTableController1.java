package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.service.AddTimeTableService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

// actor: yenvb - update timetable
@Controller
public class TimeTableController1 {

    @Autowired
    private AddTimeTableService addTimeTableService;

    @GetMapping("/import")
    public String index(Model model) {
        return "import";
    }

    @PostMapping("/import")
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
            message = addTimeTableService.addTimetable(workbook,2);
        }
        model.addAttribute("message",message.getMessage());
    }


}
