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
import java.util.List;

/**
 * lamnt98
 * 01/07
 */
@Service
public class TimeTableServicempl implements TimeTableService {

    @Autowired
    private SchoolWeekRepository schoolWeek;

    @Autowired
    private SchoolYearRepository schoolYear;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    /**
     * lamnt98
     * 01/07
     * list year, week and find yearIdCurrent, weekIdCurrent
     * @param
     * @return ListYearAndClassResponseDto
     */
    @Override
    public ListYearAndClassResponseDto getListYearAndClass() {
        ListYearAndClassResponseDto list = new ListYearAndClassResponseDto();
        List<SchoolYear> listYear = null;
        List<SchoolWeek> listWeek = null;
        Integer yearIdCurrent = null;
        Integer weekIdCurrent = null;
        List<Class> classList = null;
        MessageDTO messageDTO = new MessageDTO();

        try {
            //listWeek = schoolWeek.findAll();
            listYear = schoolYear.findAll();
            if (listYear == null) {
                messageDTO = Constant.LIST_YEAR_NULL;
                list.setMessageDTO(messageDTO);
                return list;
            }
            list.setListYear(listYear);

            Date date = Date.valueOf(LocalDate.now());
            for (int i = 0; i < listYear.size(); i++) {
                SchoolYear schoolYear = listYear.get(i);
                //find yearIdCurrent
                if (date.compareTo(schoolYear.getFromDate()) > 0 && date.compareTo(schoolYear.getToDate()) < 0) {
                    yearIdCurrent = schoolYear.getYearID();
                }
            }
            //check yearIdCurrent null or not
            if (yearIdCurrent == null) {
                messageDTO = Constant.YEAR_ID_NULL;
                list.setMessageDTO(messageDTO);
                return list;
            }
            list.setYearIdCurrent(yearIdCurrent);

            listWeek = schoolWeek.findSchoolWeeksByYearID(yearIdCurrent);

            //check listWeek null or not
            if (listWeek == null) {
                messageDTO = Constant.LIST_WEEK_NULL;
                list.setMessageDTO(messageDTO);
                return list;
            }
            list.setListWeek(listWeek);


            for (int i = 0; i < listWeek.size(); i++) {
                SchoolWeek schoolWeek = listWeek.get(i);
                //find weekIdCurrent
                if (date.compareTo(schoolWeek.getFromDate()) > 0 && date.compareTo(schoolWeek.getToDate()) < 0) {
                    weekIdCurrent = schoolWeek.getWeekID();
                    break;
                }
            }

            //check weekIdCurrent null or not
            if (weekIdCurrent == null) {
                messageDTO = Constant.WEEK_ID_NULL;
                list.setMessageDTO(messageDTO);
                return list;
            }
            list.setWeekIdCurrent(weekIdCurrent);


            classList = classRepository.findAll();

            //check classList null or not
            if (classList == null) {
                messageDTO = Constant.CLASSLIST_NOT_EXIT;
                list.setMessageDTO(messageDTO);
                return list;
            }

            list.setClassList(classList);
            messageDTO = Constant.SUCCESS;
            list.setMessageDTO(messageDTO);
        }catch (Exception e){
            messageDTO.setMessageCode(1);
            messageDTO.setMessage(e.toString());
            list.setMessageDTO(messageDTO);
            return list;
        }

        return list;
    }

    /**
     * lamnt98
     * 01/07
     * find list week by yearIdCurrent
     * @param listWeekRequestDto
     * @return ListWeekResponseDto
     */
    @Override
    public ListWeekResponseDto getListWeekByYearId(ListWeekRequestDto listWeekRequestDto) {
        Integer yearIdCurrent = listWeekRequestDto.getYearIdCurrent();
        ListWeekResponseDto listWeekResponseDto = new ListWeekResponseDto();
        List<SchoolWeek> listWeek;
        MessageDTO messageDTO = new MessageDTO();

        try {
            //check yearIdCurrent null or not
            if (yearIdCurrent == null) {
                messageDTO = Constant.YEAR_ID_NULL;
                listWeekResponseDto.setMessageDTO(messageDTO);
                return listWeekResponseDto;
            }

            listWeek = schoolWeek.findSchoolWeeksByYearID(yearIdCurrent);

            //check listWeek null or not
            if (listWeek.size() == 0) {
                messageDTO = Constant.LIST_WEEK_NULL;
                listWeekResponseDto.setMessageDTO(messageDTO);
                return listWeekResponseDto;
            }
            listWeekResponseDto.setListWeek(listWeek);
            messageDTO = Constant.SUCCESS;
            listWeekResponseDto.setMessageDTO(messageDTO);
        }catch (Exception e){
            messageDTO.setMessageCode(1);
            messageDTO.setMessage(e.toString());
            listWeekResponseDto.setMessageDTO(messageDTO);
            return listWeekResponseDto;
        }
        return listWeekResponseDto;
    }

    /**
     * lamnt98
     * 01/07
     * find timetable of class by weekId and classId
     * @param classTimeTable
     * @return ClassTimeTableResponseDto
     */
    @Override
    public ClassTimeTableResponseDto getClassTimeTable(ClassTimeTableRequestDto classTimeTable) {
        ClassTimeTableResponseDto timeTabel = new ClassTimeTableResponseDto();
        List<TimeTable> morningTimeTable;
        List<TimeTable> afternoonTimeTable;
        MessageDTO messageDTO = new MessageDTO();
        Integer weekId;
        Integer classId;
        Integer biggetClosetApplyWeek;

        try {
            weekId = classTimeTable.getWeekId();

            //check weekId null or not
            if(weekId == null){
                messageDTO = Constant.WEEK_ID_NULL;
                timeTabel.setMessageDTO(messageDTO);
                return  timeTabel;
            }

            classId = classTimeTable.getClassId();

            //check classId null or not
            if(classId == null){
                messageDTO = Constant.CLASS_ID_NULL;
                timeTabel.setMessageDTO(messageDTO);
                return  timeTabel;
            }

            biggetClosetApplyWeek = timetableRepository.getBiggestClosetApplyWeek(weekId,classId);
            //check biggestClosetApplyWeek
            if(biggetClosetApplyWeek == null){
                messageDTO = Constant.TIMETABLE_NULL;
                timeTabel.setMessageDTO(messageDTO);
                return  timeTabel;
            }

            morningTimeTable = timetableRepository.getMorningTimeTable(biggetClosetApplyWeek,classId);
            afternoonTimeTable = timetableRepository.getAfternoonTimeTable(biggetClosetApplyWeek,classId);

            //check timetable exist or not
            if(morningTimeTable.size() == 0 && afternoonTimeTable.size() == 0){
                messageDTO = Constant.TIMETABLE_NULL;
                timeTabel.setMessageDTO(messageDTO);
                return  timeTabel;
            }

            messageDTO = Constant.SUCCESS;
            timeTabel.setMessageDTO(messageDTO);
            timeTabel.setMorningTimeTable(morningTimeTable);
            timeTabel.setAfternoonTimeTable(afternoonTimeTable);
        }catch (Exception e){
            messageDTO.setMessageCode(1);
            messageDTO.setMessage(e.toString());
            timeTabel.setMessageDTO(messageDTO);
            return  timeTabel;
        }
        return timeTabel;
    }

    /**
     * lamnt98
     * 02/07
     * list year, week, class and find yearIdCurrent, weekIdCurrent
     * @param
     * @return ListYearAndTeacherResponseDto
     */
    @Override
    public ListYearAndTeacherResponseDto getListYearAndTeacher() {
        ListYearAndTeacherResponseDto list = new ListYearAndTeacherResponseDto();
        List<SchoolYear> listYear = null;
        List<SchoolWeek> listWeek = null;
        Integer yearIdCurrent = null;
        Integer weekIdCurrent = null;
        List<Teacher> teacherList = null;
        MessageDTO messageDTO = new MessageDTO();

        try {
            //listWeek = schoolWeek.findAll();
            listYear = schoolYear.findAll();
            if (listYear == null) {
                messageDTO = Constant.LIST_YEAR_NULL;
                list.setMessageDTO(messageDTO);
                return list;
            }
            list.setListYear(listYear);

            Date date = Date.valueOf(LocalDate.now());
            for (int i = 0; i < listYear.size(); i++) {
                SchoolYear schoolYear = listYear.get(i);
                //find yearIdCurrent
                if (date.compareTo(schoolYear.getFromDate()) > 0 && date.compareTo(schoolYear.getToDate()) < 0) {
                    yearIdCurrent = schoolYear.getYearID();
                }
            }
            //check yearIdCurrent null or not
            if (yearIdCurrent == null) {
                messageDTO = Constant.YEAR_ID_NULL;
                list.setMessageDTO(messageDTO);
                return list;
            }
            list.setYearIdCurrent(yearIdCurrent);

            listWeek = schoolWeek.findSchoolWeeksByYearID(yearIdCurrent);

            //check listWeek null or not
            if (listWeek == null) {
                messageDTO = Constant.LIST_WEEK_NULL;
                list.setMessageDTO(messageDTO);
                return list;
            }
            list.setListWeek(listWeek);


            for (int i = 0; i < listWeek.size(); i++) {
                SchoolWeek schoolWeek = listWeek.get(i);
                //find weekIdCurrent
                if (date.compareTo(schoolWeek.getFromDate()) > 0 && date.compareTo(schoolWeek.getToDate()) < 0) {
                    weekIdCurrent = schoolWeek.getWeekID();
                    break;
                }
            }

            //check weekIdCurrent null or not
            if (weekIdCurrent == null) {
                messageDTO = Constant.WEEK_ID_NULL;
                list.setMessageDTO(messageDTO);
                return list;
            }
            list.setWeekIdCurrent(weekIdCurrent);

            teacherList = teacherRepository.findAll();

            //check classList null or not
            if (teacherList == null) {
                messageDTO = Constant.TEACHERLIST_NULL;
                list.setMessageDTO(messageDTO);
                return list;
            }

            list.setTeacherList(teacherList);
            messageDTO = Constant.SUCCESS;
            list.setMessageDTO(messageDTO);

        }catch (Exception e){
            messageDTO.setMessageCode(1);
            messageDTO.setMessage(e.toString());
            list.setMessageDTO(messageDTO);
            return  list;
        }
        return  list;
    }

    /**
     * lamnt98
     * 01/07
     * find timetable of teacher by weekId and teacherId
     * @param teacherTimeTable
     * @return TeacherTimeTableResponseDto
     */
    @Override
    public TeacherTimeTableResponseDto getTeacherTimeTable(TeacherTimeTableRequestDto teacherTimeTable) {
        TeacherTimeTableResponseDto timeTabel = new TeacherTimeTableResponseDto();
        List<TimeTable> morningTimeTable;
        List<TimeTable> afternoonTimeTable;
        MessageDTO messageDTO = new MessageDTO();
        Integer weekId;
        Integer teacherId;
        Integer biggetClosetApplyWeek;

        try {
            weekId = teacherTimeTable.getWeekId();

            //check weekId null or not
            if(weekId == null){
                messageDTO = Constant.WEEK_ID_NULL;
                timeTabel.setMessageDTO(messageDTO);
                return  timeTabel;
            }

            teacherId = teacherTimeTable.getTeacherId();

            //check classId null or not
            if(teacherId == null){
                messageDTO = Constant.TEACHER_ID_NULL;
                timeTabel.setMessageDTO(messageDTO);
                return  timeTabel;
            }

            biggetClosetApplyWeek = timetableRepository.getBiggestClosetApplyWeekTeacher(weekId,teacherId);
            //check biggestClosetApplyWeek
            if(biggetClosetApplyWeek == null){
                messageDTO = Constant.TIMETABLE_NULL;
                timeTabel.setMessageDTO(messageDTO);
                return  timeTabel;
            }

            morningTimeTable = timetableRepository.getTeacherMorningTimeTable(biggetClosetApplyWeek,teacherId);
            afternoonTimeTable = timetableRepository.getTeacherAfternoonTimeTable(biggetClosetApplyWeek,teacherId);

            //check timetable exist or not
            if(morningTimeTable.size() == 0 && afternoonTimeTable.size() == 0){
                messageDTO = Constant.TIMETABLE_NULL;
                timeTabel.setMessageDTO(messageDTO);
                return  timeTabel;
            }

            messageDTO = Constant.SUCCESS;
            timeTabel.setMessageDTO(messageDTO);
            timeTabel.setMorningTimeTable(morningTimeTable);
            timeTabel.setAfternoonTimeTable(afternoonTimeTable);
        }catch (Exception e){
            messageDTO.setMessageCode(1);
            messageDTO.setMessage(e.toString());
            timeTabel.setMessageDTO(messageDTO);
            return  timeTabel;
        }
        return timeTabel;
    }
}
