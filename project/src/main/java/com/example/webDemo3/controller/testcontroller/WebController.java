package com.example.webDemo3.controller.testcontroller;

import com.example.webDemo3.repository.UserRepository;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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


    @GetMapping("/") // Nếu người dùng request tới địa chỉ "/"
    public String index(Model model) {
       // model.addAttribute("userList", userRepository.findAll());
        return "import"; // Trả về file index.html
    }

    @PostMapping("/import")
    public void mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile,Model model) throws IOException {

      //  List<Test> tempStudentList = new ArrayList<Test>();
      //  XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        HSSFWorkbook workbook = new HSSFWorkbook(reapExcelDataFile.getInputStream());
    //    XSSFSheet worksheet = workbook.getSheetAt(1);
        HSSFSheet worksheet = workbook.getSheetAt(1);
                // workbook.getSheet("a");
        List<String> test = new ArrayList<>();
        int coll = worksheet.getRow(3).getPhysicalNumberOfCells();
        //worksheet.getPhysicalNumberOfRows()
        int j;
        for(int i=3;i<35;i++) {
            try {
                //            Test tempStudent = new Test();
//            tempStudent.setContent(row.getCell(1).getStringCellValue());
//            tempStudentList.add(tempStudent);
                HSSFRow row = worksheet.getRow(i);
              //  HSSFRow c = worksheet.getCol;
//            String k=  row.getCell(4).getStringCellValue();
//            test.add(k);
                for (j=3 ; j < coll;j++){
                    String s=  row.getCell(j).getStringCellValue();
                    test.add(s);
                    if(s.equalsIgnoreCase("Minh")){
                        coll = j+1;
                    }
                }
            }catch (Exception e){
                model.addAttribute("exception",e);
            }

        }
        model.addAttribute("test",test);
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