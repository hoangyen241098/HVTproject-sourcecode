package com.example.webDemo3.controller.testcontroller;

import com.example.webDemo3.entity.TimeTable;
import com.example.webDemo3.repository.TimetableRepository;
import com.example.webDemo3.repository.UserRepository;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WebController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TimetableRepository timetableRepository;


    @GetMapping("/") // Nếu người dùng request tới địa chỉ "/"
    public String index(Model model) {
        // model.addAttribute("userList", userRepository.findAll());
        return "import"; // Trả về file index.html
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

    public void xuli1(HSSFSheet worksheet) {
        List<String> teachers = new ArrayList<>();
        List<String> classes = new ArrayList<>();
        int coll = 50;
        int row = 35;
        String [][] subjects = new String[50][50];
        List<List<String>> subjectList = new ArrayList<List<String>>();
        for (int j = 3; j < coll; j++) {
            String teacher = getData(worksheet,3,j);
            if (teacher == null || teacher.equalsIgnoreCase("")){
                break;
            }
            teachers.add(teacher);
            classes.add(getData(worksheet,4,j));
            int d = teachers.size();
            int c = 1;
            List<String> sublist = new ArrayList<>();
            for (int i = 5; i < row; i++) {
                String s = getData(worksheet,i,j);
                subjects[d][c] = s;
                c++;
                sublist.add(s);
            }
            subjectList.add(sublist);
        }
        System.out.println(teachers.size());

        String gv = "",mh;
        String test = subjects[1][2];
        int vt = test.indexOf("-");
        if(vt == -1){
            mh=test;
        }
        else{
            mh=test.substring(0,vt);
            gv=test.substring(vt+1,test.length());
        }


        TimeTable tb = new TimeTable();
        tb.setClassId(2);
        tb.setSlot(1);
        tb.setDayId(1);
        tb.setSubject(mh);
        tb.setWeekApply(1);
        timetableRepository.save(tb);

        System.out.println(mh);
        System.out.println(gv);
        System.out.println(test);
    }

    @PostMapping("/import")
    public void mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile, Model model) throws IOException {

        //  List<Test> tempStudentList = new ArrayList<Test>();
        //  XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        HSSFWorkbook workbook = new HSSFWorkbook(reapExcelDataFile.getInputStream());
        //    XSSFSheet worksheet = workbook.getSheetAt(1);
        HSSFSheet worksheet = workbook.getSheetAt(0);
        xuli1(worksheet);
        // workbook.getSheet("a");
//        List<String> test = new ArrayList<>();
//        int coll = worksheet.getRow(3).getPhysicalNumberOfCells();
//        //worksheet.getPhysicalNumberOfRows()
//        int j;
//        for(int i=3;i<35;i++) {
//            try {
//                //            Test tempStudent = new Test();
////            tempStudent.setContent(row.getCell(1).getStringCellValue());
////            tempStudentList.add(tempStudent);
//                HSSFRow row = worksheet.getRow(i);
//              //  HSSFRow c = worksheet.getCol;
////            String k=  row.getCell(4).getStringCellValue();
////            test.add(k);
//                for (j=3 ; j < coll;j++){
//                    String s=  row.getCell(j).getStringCellValue();
//                    test.add(s);
//                    if(s.equalsIgnoreCase("Minh")){
//                        coll = j+1;
//                    }
//                }
//            }catch (Exception e){
//                model.addAttribute("exception",e);
//            }
//
//        }
//        model.addAttribute("test",test);
       // return "import";
    }

    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model) {
        return "forgotPassword";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model) {
        return "changePassword";
    }

    @GetMapping("/manageAccount")
    public String manageAccount(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "manageAccount";
    }

}