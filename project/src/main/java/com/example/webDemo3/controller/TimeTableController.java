package com.example.webDemo3.controller;

import com.example.webDemo3.entity.TimeTable;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        //  XSSFSheet worksheet = workbook.getSheetAt(1);
        HSSFWorkbook workbook = new HSSFWorkbook(reapExcelDataFile.getInputStream());
        HSSFSheet worksheet = workbook.getSheetAt(0);
       // xuli1(worksheet);
        getTimetableMorning(worksheet);
    }

    public void getTimetableMorning(HSSFSheet worksheet){
        List<String> teacherList = new ArrayList<>();
        List<String> classeList = new ArrayList<>();
        List<List<String>> dataList = new ArrayList<List<String>>();


        int row = 35;
        int j=3;
        //int coll = 50;
        //for (int j = 3; j < coll; j++) {
        while (true){
            String teacher = getData(worksheet,3,j);
            if (teacher == null || teacher.equalsIgnoreCase("")){
                break;
            }
            teacherList.add(teacher);
            classeList.add(getData(worksheet,4,j));
            List<String> subDatalist = new ArrayList<>();
            for (int i = 5; i < row; i++) {
                String s = getData(worksheet,i,j);
                subDatalist.add(s);
            }
            dataList.add(subDatalist);
            j++;
        }

        System.out.println(teacherList.size());

        for(int i = 0 ; i <= dataList.size();i++){
            String gvcn = teacherList.get(i);
            String lop = classeList.get(i);
            List<String> subdata = dataList.get(i);
            for(String data : subdata){
                int vt = data.indexOf("-");
                String mh,gv=null;
                if(vt == -1){
                    mh=data;
                }
                else{
                    mh=data.substring(0,vt);
                    gv=data.substring(vt+1,data.length());
                }
                try {
                    TimeTable tb = new TimeTable();
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }
    }

    public String getData(HSSFSheet worksheet,int i,int j){
        String data = null;
        try {
            data = worksheet.getRow(i).getCell(j).getStringCellValue();
        }catch (Exception e){
            System.out.println(e);
        }
        return data;
    }
}
