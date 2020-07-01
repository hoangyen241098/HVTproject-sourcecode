package com.example.webDemo3.service.impl;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.Teacher;
import com.example.webDemo3.entity.TimeTable;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.repository.TeacherRepository;
import com.example.webDemo3.repository.TimetableRepository;
import com.example.webDemo3.service.AddTimeTableService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddTimeTableServiceImpl implements AddTimeTableService {
    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassRepository classRepository;

    @Override
    public MessageDTO addTimetable(HSSFWorkbook workbook,int applyWeekId) {
        MessageDTO message = new MessageDTO();
        try {
            HSSFSheet worksheet = workbook.getSheet("TKB Sang");
                    //.getSheetAt(0);
            message = addTimetableMorning(worksheet,applyWeekId);
        }catch (Exception e){
            System.out.println(e);
            if(message == null){
                message.setMessageCode(1);
                message.setMessage("không có sheet TKB Sang");
            }
            else{
                message.setMessageCode(1);
                message.setMessage(e.toString());
            }
        } ;
        return message;
    }

    public MessageDTO addTimetableMorning(HSSFSheet worksheet,int applyWeekId) throws Exception {
        MessageDTO message = new MessageDTO();

        List<String> teacherList = new ArrayList<>();
        List<String> classList = new ArrayList<>();
        List<List<String>> dataList = new ArrayList<List<String>>();

        int row = 35;
        int j = 3;
        //int coll = 50;
        //for (int j = 3; j < coll; j++) {
        while (true) {
            String teacherItem = getData(worksheet, 3, j);
            String classItem = getData(worksheet, 4, j);
            if (teacherItem == null || teacherItem.trim().isEmpty()
                    || classItem == null || classItem.trim().isEmpty()) {
                break;
            }
            teacherList.add(teacherItem);
            classList.add(classItem);
            List<String> subDatalist = new ArrayList<>();
            for (int i = 5; i < row; i++) {
                String s = getData(worksheet, i, j);
                subDatalist.add(s);
            }
            dataList.add(subDatalist);
            j++;
        }

        System.out.println(teacherList.size());

        for (int i = 0; i < dataList.size(); i++) {
            String gvcn = teacherList.get(i);

            String lop = classList.get(i);
            Class classTb = classRepository.findByClassIdentifier(lop);
            if(classTb == null){
                System.out.println("không tìm thấy lớp " + lop);
                message.setMessageCode(1);
                message.setMessage("không tìm thấy lớp " + lop);
//                return message;
            }

            List<String> subData = dataList.get(i);
            for (int k = 0; k < subData.size(); k++) {
                String data = subData.get(k);

                if (data == null || data.trim().isEmpty()) {
                    continue;
                }

                int vt = data.indexOf("-");
                String mh, gv = null;
                if (vt == -1) {
                    mh = data;
                } else {
                    mh = data.substring(0, vt);
                    gv = data.substring(vt + 1);
                }

                Teacher teacherTb = null;
                if (gv != null) {
                    teacherTb = teacherRepository.findTeacherTeacherIdentifier(gv);
                    if(teacherTb == null){
                        System.out.println("không tìm thấy giáo viên " + gv);
                        message.setMessageCode(1);
                        message.setMessage("không tìm thấy giáo viên " + gv);
                        return message;
                    }
                }

                int slot = k % 5 + 1;
                //if (slot == 0) slot = 5;


                int day = 1;
                if (k > 0) day = k / 5 + 1;

                try {
                    TimeTable tb = new TimeTable();
                    if (teacherTb != null) {
                        tb.setTeacherId(teacherTb.getTeacherId());
                    }
                    tb.setClassId(classTb.getClassId());
                    tb.setSlot(slot);
                    tb.setDayId(day);
                    tb.setSubject(mh);
                    tb.setWeekApply(applyWeekId);
                    tb.setIsAfternoon(0);
                    timetableRepository.save(tb);
                } catch (Exception e) {
                    System.out.println(e);
                    message.setMessageCode(1);
                    message.setMessage("không thêm được data");
                    return message;
                }
            }
        }
        System.out.println("thành công");
        message.setMessageCode(0);
        message.setMessage("thành công");
        return message;
    }



    public String getData(HSSFSheet worksheet, int i, int j) {
        String data = null;
        try {
            data = worksheet.getRow(i).getCell(j).getStringCellValue();
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }
}