package com.example.webDemo3.controller;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.Teacher;
import com.example.webDemo3.entity.TimeTable;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.repository.TeacherRepository;
import com.example.webDemo3.repository.TimetableRepository;
import com.example.webDemo3.service.TimetableService;
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
public class TimeTableController1 {

//    @Autowired
//    private TimetableRepository timetableRepository;
//
//    @Autowired
//    private TeacherRepository teacherRepository;
//
//    @Autowired
//    private ClassRepository classRepository;

    @Autowired
    private TimetableService timetableService;


    @GetMapping("/import")
    public String index(Model model) {
//        MessageDTO message = new MessageDTO();
//        model.addAttribute("message",message);
        return "import";
    }

    @PostMapping("/import")
    public void mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile, Model model)
            //throws IOException
    {

        //  List<Test> tempStudentList = new ArrayList<Test>();
        //  XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        //  XSSFSheet worksheet = workbook.getSheetAt(1);
        MessageDTO message = new MessageDTO();
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(reapExcelDataFile.getInputStream());
            message = timetableService.addTimetable(workbook,1);
//            HSSFSheet worksheet = workbook.getSheetAt(0);
//            timetableService.addTimetableMorning(worksheet);
        }catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.getMessage());
            System.out.println(e);
        }
//        message = Constant.SUCCESS;
        model.addAttribute("message",message);
    }

//    public void getTimetableMorning(HSSFSheet worksheet){
//        List<String> teacherList = new ArrayList<>();
//        List<String> classeList = new ArrayList<>();
//        List<List<String>> dataList = new ArrayList<List<String>>();
//
//
//        int row = 35;
//        int j=3;
//        //int coll = 50;
//        //for (int j = 3; j < coll; j++) {
//        while (true){
//            String teacher = getData(worksheet,3,j);
//            if (teacher == null || teacher.equalsIgnoreCase("")){
//                break;
//            }
//            teacherList.add(teacher);
//            classeList.add(getData(worksheet,4,j));
//            List<String> subDatalist = new ArrayList<>();
//            for (int i = 5; i < row; i++) {
//                String s = getData(worksheet,i,j);
//                subDatalist.add(s);
//            }
//            dataList.add(subDatalist);
//            j++;
//        }
//
//        System.out.println(teacherList.size());
//
//        for(int i = 0 ; i <= dataList.size();i++) {
//            String gvcn = teacherList.get(i);
//
//            String lop = classeList.get(i);
//            Class classTb = classRepository.findByClassIdentifier(lop);
//            List<String> subdata = dataList.get(i);
//            for (int k = 0; k <= subdata.size(); k++) {
//                String data = subdata.get(k);
//
//                if (data == null || data.trim().isEmpty()){
//                    continue;
//                }
//
//                int vt = data.indexOf("-");
//                String mh, gv = null;
//                if (vt == -1) {
//                    mh = data;
//                } else {
//                    mh = data.substring(0, vt);
//                    gv = data.substring(vt + 1);
//                }
//
//                Teacher teacherTb = null;
//                if(gv != null){
//                    teacherTb = teacherRepository.findTeacherTeacherIdentifier(gv);
//                }
//
//                int slot = k % 5 + 1;
//                //if (slot == 0) slot = 5;
//
//
//                int day = 1;
//                if (k > 0) day = k / 5 + 1;
//
//                try {
//                    TimeTable tb = new TimeTable();
//                    if (teacherTb != null){
//                        tb.setTeacherId(teacherTb.getTeacherId());
//                    }
//                    tb.setClassId(classTb.getClassId());
//                    tb.setSlot(slot);
//                    tb.setDayId(day);
//                    tb.setSubject(mh);
//                    tb.setWeekApply(1);
//                    tb.setIsAfternoon(0);
//                    timetableRepository.save(tb);
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//            }
//        }
//    }
//
//    public String getData(HSSFSheet worksheet,int i,int j){
//        String data = null;
//        try {
//            data = worksheet.getRow(i).getCell(j).getStringCellValue();
//        }catch (Exception e){
//            System.out.println(e);
//        }
//        return data;
//    }
}
