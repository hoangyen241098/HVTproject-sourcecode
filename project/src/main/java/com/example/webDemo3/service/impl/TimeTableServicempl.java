package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.*;
import com.example.webDemo3.dto.request.ClassTimeTableRequestDto;
import com.example.webDemo3.dto.request.TeacherTimeTableRequestDto;
import com.example.webDemo3.entity.*;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.repository.*;
import com.example.webDemo3.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
     * view timetable of class by currentDate and classId default
     * @param
     * @return ViewClassTimeTableResponseDto
     */
    @Override
    public ViewClassTimeTableResponseDto viewClassTimeTable() {
        ViewClassTimeTableResponseDto timeTabel = new ViewClassTimeTableResponseDto();
        List<List<MorInforTimeTableDto>> morningTimeTableList = new ArrayList<>();
        List<List<AfterInforTimeTableDto>> afternoonTimeTableTableList = new ArrayList<>();
        MessageDTO messageDTO = new MessageDTO();
        Date currentDate = null;
        Integer classId = null;
        List<Date> appyDateList;
        List<TimeTable> morningTimeTable = null;
        List<TimeTable> afternoonTimeTable = null;
        List<Class> classList = null;

        try {
            appyDateList = timetableRepository.getAllDate();

            if(appyDateList.size() != 0){
                currentDate = appyDateList.get(0);
                timeTabel.setCurrentDate(currentDate);
            }

            timeTabel.setAppyDateList(appyDateList);

            classList = classRepository.findAll();

            //check classId null or not and classList empty or not
            if(classList.size() != 0){
                classId = classList.get(0).getClassId();
                timeTabel.setClassId(classId);
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
     * 07/09
     * view timetable of teacher by currentDate and teacherId default
     * @param
     * @return ViewTeacherTimeTableReponseDto
     */
    @Override
    public ViewTeacherTimeTableReponseDto viewTeacherTimeTable() {
        ViewTeacherTimeTableReponseDto timeTabel = new ViewTeacherTimeTableReponseDto();
        List<List<MorInforTimeTableDto>> morningTimeTableList = new ArrayList<>();
        List<List<AfterInforTimeTableDto>> afternoonTimeTableTableList = new ArrayList<>();
        MessageDTO messageDTO = new MessageDTO();
        Date currentDate = null;
        Integer teacherId = null;
        List<Date> appyDateList;
        List<TimeTable> morningTimeTable = null;
        List<TimeTable> afternoonTimeTable = null;
        List<Teacher> teacherList = null;
        Teacher teacher = null;

        try {
            appyDateList = timetableRepository.getAllDate();

            if(appyDateList.size() != 0){
                    currentDate = appyDateList.get(0);
            }
            timeTabel.setCurrentDate(currentDate);

            teacherList = teacherRepository.findAll();

            timeTabel.setAppyDateList(appyDateList);
            timeTabel.setTeacherList(teacherList);

            //check teacher list empty
            if(teacherList.size() != 0){
                teacherId = teacherList.get(0).getTeacherId();
                timeTabel.setTeacherId(teacherId);
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
     * 01/07
     * search timetable of class by currentDate and classId
     * @param classTimeTable
     * @return SearchTimeTableResponseDto
     */
    @Override
    public SearchTimeTableResponseDto searchClassTimeTable(ClassTimeTableRequestDto classTimeTable) {
        SearchTimeTableResponseDto timeTabel = new SearchTimeTableResponseDto();
        List<List<MorInforTimeTableDto>> morningTimeTableList = new ArrayList<>();
        List<List<AfterInforTimeTableDto>> afternoonTimeTableTableList = new ArrayList<>();
        MessageDTO messageDTO = new MessageDTO();
        Date currentDate = classTimeTable.getApplyDate();
        Integer classId = classTimeTable.getClassId();
        List<TimeTable> morningTimeTable = null;
        List<TimeTable> afternoonTimeTable = null;


        try {
            //check currentDate null or not
            if(currentDate == null){
                messageDTO = Constant.FROMDATE_EMPTY;
                timeTabel.setMessage(messageDTO);
                return timeTabel;
            }

            //check classId null or not
            if(classId == null){
                messageDTO = Constant.CLASS_ID_NULL;
                timeTabel.setMessage(messageDTO);
                return timeTabel;
            }
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

            //check timetable empty or not
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
     * search timetable of teacher by currentDate and teacherId
     * @param teacherTimeTable
     * @return SearchTimeTableResponseDto
     */
    @Override
    public SearchTimeTableResponseDto searchTeacherTimeTable(TeacherTimeTableRequestDto teacherTimeTable) {
        SearchTimeTableResponseDto timeTabel = new SearchTimeTableResponseDto();
        List<List<MorInforTimeTableDto>> morningTimeTableList = new ArrayList<>();
        List<List<AfterInforTimeTableDto>> afternoonTimeTableTableList = new ArrayList<>();
        MessageDTO messageDTO = new MessageDTO();
        Date currentDate = teacherTimeTable.getApplyDate();
        Integer teacherId = teacherTimeTable.getTeacherId();
        List<TimeTable> morningTimeTable = null;
        List<TimeTable> afternoonTimeTable = null;
        Teacher teacher = null;

        try {

            //check currentDate null or not
            if(currentDate == null){
                messageDTO = Constant.FROMDATE_EMPTY;
                timeTabel.setMessage(messageDTO);
                return timeTabel;
            }

            //check classId null or not
            if(teacherId == null){
                messageDTO = Constant.TEACHER_ID_NULL;
                timeTabel.setMessage(messageDTO);
                return timeTabel;
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

            //check morningTimeTable empty or not
            if(morningTimeTable.size() != 0){
                morningTimeTableList.add(changeMorningTimeTable(morningTimeTable));
            }

            //check afternoonTimeTable empty or not
            if(afternoonTimeTable.size() != 0){
                afternoonTimeTableTableList.add(changeAfternoonTimeTable(afternoonTimeTable));
            }

            //check timetable emty or not
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
