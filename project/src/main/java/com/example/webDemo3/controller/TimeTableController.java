package com.example.webDemo3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TimeTableController {
    @GetMapping("/import")
    public String index(Model model) {
        return "import";
    }

    @PostMapping("/import")
    public void mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile, Model model) throws IOException {

        //  List<Test> tempStudentList = new ArrayList<Test>();
        //  XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        //    XSSFSheet worksheet = workbook.getSheetAt(1);
        HSSFWorkbook workbook = new HSSFWorkbook(reapExcelDataFile.getInputStream());
        HSSFSheet worksheet = workbook.getSheetAt(0);
        xuli1(worksheet);
    }
}
