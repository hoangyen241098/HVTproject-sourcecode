package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.ClassTimeTableRequestDto;
import com.example.webDemo3.dto.request.ListWeekRequestDto;
import com.example.webDemo3.dto.request.TeacherTimeTableRequestDto;
import com.example.webDemo3.entity.*;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.repository.*;
import com.example.webDemo3.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * lamnt98
 * 01/07
 */
@Service
public class TimeTableServicempl implements TimeTableService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private TeacherRepository teacherRepository;


    /**
     * lamnt98
     * 01/07
     * find timetable of class by weekId and classId
     * @param classTimeTable
     * @return TimeTableResponseDto
     */
    @Override
    public TimeTableResponseDto getClassTimeTable(ClassTimeTableRequestDto classTimeTable) {
        TimeTableResponseDto timeTabel = new TimeTableResponseDto();
        List<List<MorInforTimeTableDto>> morningTimeTableList = new ArrayList<>();
        List<List<AfterInforTimeTableDto>> afternoonTimeTableTableList = new ArrayList<>();
        MessageDTO messageDTO = new MessageDTO();
        Date currentDate = classTimeTable.getApplyDate();
        Integer classId;
        List<Date> appyDateList;
        List<TimeTable> morningTimeTable = null;
        List<TimeTable> afternoonTimeTable = null;
        List<Class> classList = null;

        try {
            appyDateList = timetableRepository.getAllDate();


            if(appyDateList.size() != 0){
                if(currentDate == null){
                    currentDate = appyDateList.get(0);
                }
            }
            timeTabel.setCurrentDate(currentDate);
            timeTabel.setAppyDateList(appyDateList);

            classList = classRepository.findAll();

            classId = classTimeTable.getClassId();

            //check classId null or not and classList empty or not
            if(classId == null && classList.size() != 0){
                classId = classList.get(0).getClassId();
            }

            timeTabel.setClassList(classList);

            //check class exists or not
            if(classRepository.findByClassId(classId) == null){
                messageDTO = Constant.CLASS_NOT_EXIST;
                timeTabel.setMessage(messageDTO);
                return  timeTabel;
            }

            for(int i = 0; i < 4; i++){
                morningTimeTable = timetableRepository.getMorningClassTimeTable(currentDate,classId,i);
                afternoonTimeTable = timetableRepository.getAfternoonClassTimeTable(currentDate,classId,i);

                if(morningTimeTable.size() == 0 && afternoonTimeTable.size() == 0 ){
                    break;
                }

                if(morningTimeTable.size() != 0){
                    morningTimeTableList.add(changeMorningTimeTable(morningTimeTable));
                }

                if(afternoonTimeTable.size() != 0){
                    afternoonTimeTableTableList.add(changeAfternoonTimeTable(afternoonTimeTable));
                }
            }

            if(morningTimeTableList.size() == 0 && afternoonTimeTableTableList.size() == 0 ){
                messageDTO = Constant.TIMETABLE_NULL;
                timeTabel.setMessage(messageDTO);
                return  timeTabel;
            }

            timeTabel.setMorningTimeTableList(morningTimeTableList);
            timeTabel.setAfternoonTimeTableTableList(afternoonTimeTableTableList);
            messageDTO = Constant.SUCCESS;
            timeTabel.setMessage(messageDTO);
        }catch (Exception e){
            messageDTO.setMessageCode(1);
            messageDTO.setMessage(e.toString());
            timeTabel.setMessage(messageDTO);
            return  timeTabel;
        }
        return timeTabel;
    }

    /**
     * lamnt98
     * 01/07
     * find timetable of teacher by weekId and teacherId
     * @param teacherTimeTable
     * @return TimeTableResponseDto
     */
    @Override
    public TimeTableResponseDto getTeacherTimeTable(TeacherTimeTableRequestDto teacherTimeTable) {
        TimeTableResponseDto timeTabel = new TimeTableResponseDto();
        List<List<MorInforTimeTableDto>> morningTimeTableList = new ArrayList<>();
        List<List<AfterInforTimeTableDto>> afternoonTimeTableTableList = new ArrayList<>();
        MessageDTO messageDTO = new MessageDTO();
        Date currentDate = teacherTimeTable.getApplyDate();
        Integer teacherId;
        List<Date> appyDateList;
        List<TimeTable> morningTimeTable = null;
        List<TimeTable> afternoonTimeTable = null;
        List<Teacher> teacherList = null;
        Teacher teacher = null;

        try {
            appyDateList = timetableRepository.getAllDate();

            if(appyDateList.size() != 0){
                if(currentDate == null){
                    currentDate = appyDateList.get(0);
                }
            }
            timeTabel.setCurrentDate(currentDate);

            teacherList = teacherRepository.findAll();

            timeTabel.setAppyDateList(appyDateList);
            timeTabel.setTeacherList(teacherList);

            teacherId = teacherTimeTable.getTeacherId();

            //check teacher null or not
            if(teacherId == null && teacherList.size() != 0){
                teacherId = teacherList.get(0).getTeacherId();
            }

            teacher = teacherRepository.findById(teacherId).orElse(null);
            //check teacher exists or not
            if( teacher== null){
                messageDTO = Constant.TEACHER_NOT_EXIT;
                timeTabel.setMessage(messageDTO);
                return  timeTabel;
            }


            morningTimeTable = timetableRepository.getMorningTeacherTimeTable(currentDate,teacherId);
            afternoonTimeTable = timetableRepository.getAfternoonTeacherTimeTable(currentDate,teacherId);

            if(morningTimeTable.size() != 0){
                morningTimeTableList.add(changeMorningTimeTable(morningTimeTable));
            }

            if(afternoonTimeTable.size() != 0){
                afternoonTimeTableTableList.add(changeAfternoonTimeTable(afternoonTimeTable));
            }

            if(morningTimeTableList.size() == 0 && afternoonTimeTableTableList.size() == 0 ){
                messageDTO = Constant.TIMETABLE_NULL;
                timeTabel.setMessage(messageDTO);
                return  timeTabel;
            }

            timeTabel.setMorningTimeTableList(morningTimeTableList);
            timeTabel.setAfternoonTimeTableTableList(afternoonTimeTableTableList);
            messageDTO = Constant.SUCCESS;
            timeTabel.setMessage(messageDTO);
        }catch (Exception e){
            messageDTO.setMessageCode(1);
            messageDTO.setMessage(e.toString());
            timeTabel.setMessage(messageDTO);
            return  timeTabel;
        }
        return timeTabel;
    }

    /**
     * lamnt98
     * 03/07
     * change MorningTimetable from Timetable to ResponseDto
     * @param
     * @return List<MorInforTimeTableDto>
     */
    public List<MorInforTimeTableDto> changeMorningTimeTable(List<TimeTable> list){
        List<MorInforTimeTableDto> listMorning = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Teacher teacher = null;
            TimeTable timeTable = list.get(i);
            MorInforTimeTableDto morInforTimeTable = new MorInforTimeTableDto();

            Integer teacherId = timeTable.getTeacherId();

            //check teacherId empty or not
            if(teacherId != null){
                teacher = teacherRepository.findById(teacherId).orElse(null);
            }

            //cheack teacher rmpty or not
            if(teacher != null){
                morInforTimeTable.setTeacherIdentifier(teacher.getTeacherIdentifier());
            }

            Class newClass = classRepository.findById(timeTable.getClassId()).orElse(null);

            //check class empty or not
            if(newClass != null){
                morInforTimeTable.setClassIdentifier(newClass.getClassIdentifier());
            }

            morInforTimeTable.setSlotId(timeTable.getSlot());
            morInforTimeTable.setDayId(timeTable.getDayId());
            morInforTimeTable.setSubject(timeTable.getSubject());
            morInforTimeTable.setIsAdditional(timeTable.getIsAdditional());

            listMorning.add(morInforTimeTable);
        }
        return  listMorning;
    }

    /**
     * lamnt98
     * 03/07
     * change AfternoonTimeTable from Timetable to ResponseDto
     * @param
     * @return List<AfterInforTimeTableDto>
     */
    public List<AfterInforTimeTableDto> changeAfternoonTimeTable(List<TimeTable> list){
        List<AfterInforTimeTableDto> listAfternoon = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            TimeTable timeTable = list.get(i);
            AfterInforTimeTableDto afternoonTimeTable = new AfterInforTimeTableDto();
            Teacher teacher = null;

            Integer teacherId = timeTable.getTeacherId();

            //check teacherId empty or not
            if(teacherId != null){
                teacher = teacherRepository.findById(teacherId).orElse(null);
            }

            //check teacher emmpty or not
            if(teacher != null){
                afternoonTimeTable.setTeacherIdentifier(teacher.getTeacherIdentifier());
            }

            Class newClass = classRepository.findById(timeTable.getClassId()).orElse(null);

            //check class empty or not
            if(newClass != null){
                afternoonTimeTable.setClassIdentifier(newClass.getClassIdentifier());
            }

            afternoonTimeTable.setSlotId(timeTable.getSlot());
            afternoonTimeTable.setDayId(timeTable.getDayId());
            afternoonTimeTable.setSubject(timeTable.getSubject());
            afternoonTimeTable.setIsOddWeek(timeTable.getIsOddWeek());
            afternoonTimeTable.setIsAdditional(timeTable.getIsAdditional());
            listAfternoon.add(afternoonTimeTable);
        }
        return  listAfternoon;
    }
}
