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
    private SchoolYearRepository schoolYear;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TimeTableWeekRepository timeTableWeekRepository;

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
        ListYearAndWeekResponseDto listYearAndWeek = new ListYearAndWeekResponseDto();
        List<Class> classList = null;
        MessageDTO messageDTO = new MessageDTO();

        try {
            listYearAndWeek = getListYearAndListWeek();

            //check listYear empty or not
            if(listYearAndWeek.getListYear().size() != 0){
                list.setListYear(listYearAndWeek.getListYear());
            }

            //check yearIdCurrent empty or not
            if(listYearAndWeek.getYearIdCurrent() != null){
                list.setYearIdCurrent(listYearAndWeek.getYearIdCurrent());
            }

            //check listWeek empty or not
            if(listYearAndWeek.getListWeek().size() != 0){
                list.setListWeek(listYearAndWeek.getListWeek());
            }

            //check weekIdCurrent empty or not
            if(listYearAndWeek.getWeekIdCurrent() != null){
                list.setWeekIdCurrent(listYearAndWeek.getWeekIdCurrent());
            }

            classList = classRepository.findAll();
            //check classList empty or not
            if(classList.size() != 0){
                list.setClassList(classList);
            }
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
        List<TimeTableWeek> listWeek;
        MessageDTO messageDTO = new MessageDTO();

        try {
            //check yearIdCurrent null or not
            if (yearIdCurrent == null) {
                messageDTO = Constant.YEAR_ID_NULL;
                listWeekResponseDto.setMessageDTO(messageDTO);
                return listWeekResponseDto;
            }

            listWeek = timeTableWeekRepository.findByYearIdANdSortByFromDate(yearIdCurrent);

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
     * @return TimeTableResponseDto
     */
    @Override
    public TimeTableResponseDto getClassTimeTable(ClassTimeTableRequestDto classTimeTable) {
        TimeTableResponseDto timeTabel = new TimeTableResponseDto();
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

            //check morningTimeTable empty or not
            if(morningTimeTable.size() != 0){
                timeTabel.setMorningTimeTable(changeMorningTimeTable(morningTimeTable));
            }

            //check afternoonTimeTable empty or not
            if(afternoonTimeTable.size() != 0){
                timeTabel.setAfternoonTimeTable(changeAfternoonTimeTable(afternoonTimeTable));
            }
            //check timetable exist or not
            if(morningTimeTable.size() == 0 && afternoonTimeTable.size() == 0){
                messageDTO = Constant.TIMETABLE_NULL;
                timeTabel.setMessageDTO(messageDTO);
                return  timeTabel;
            }


            messageDTO = Constant.SUCCESS;
            timeTabel.setMessageDTO(messageDTO);
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
        ListYearAndWeekResponseDto listYearAndWeek = new ListYearAndWeekResponseDto();
        List<Teacher> teacherList = null;
        MessageDTO messageDTO = new MessageDTO();

        try {
            listYearAndWeek = getListYearAndListWeek();

            //cehck listYear empty or not
            if(listYearAndWeek.getListYear().size() != 0){
                list.setListYear(listYearAndWeek.getListYear());
            }

            //check yearIdCurrent empty or not
            if(listYearAndWeek.getYearIdCurrent() != null){
                list.setYearIdCurrent(listYearAndWeek.getYearIdCurrent());
            }

            //check listWeek empty or not
            if(listYearAndWeek.getListWeek().size() != 0){
                list.setListWeek(listYearAndWeek.getListWeek());
            }

            //check weekIdCurrent empty or not
            if(listYearAndWeek.getWeekIdCurrent() != null){
                list.setWeekIdCurrent(listYearAndWeek.getWeekIdCurrent());
            }

            teacherList = teacherRepository.findAll();

            //check classList null or not
            if (teacherList.size() != 0) {
                list.setTeacherList(teacherList);
            }
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
     * @return TimeTableResponseDto
     */
    @Override
    public TimeTableResponseDto getTeacherTimeTable(TeacherTimeTableRequestDto teacherTimeTable) {
        TimeTableResponseDto timeTabel = new TimeTableResponseDto();
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

            //cehck morningTimeTable empty or not
            if(morningTimeTable.size() != 0){
                timeTabel.setMorningTimeTable(changeMorningTimeTable(morningTimeTable));
            }

            //check afternoonTimeTable empty or not
            if(afternoonTimeTable.size() != 0){
                timeTabel.setAfternoonTimeTable(changeAfternoonTimeTable(afternoonTimeTable));
            }
            //check timetable exist or not
            if(morningTimeTable.size() == 0 && afternoonTimeTable.size() == 0){
                messageDTO = Constant.TIMETABLE_NULL;
                timeTabel.setMessageDTO(messageDTO);
                return  timeTabel;
            }

            messageDTO = Constant.SUCCESS;
            timeTabel.setMessageDTO(messageDTO);
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
     * 03/07
     * get List year , week and yearIdCurrent, weekIdCurrent
     * @param
     * @return ListYearAndWeekResponseDto
     */
    public ListYearAndWeekResponseDto getListYearAndListWeek(){
        ListYearAndWeekResponseDto list = new ListYearAndWeekResponseDto();
        List<SchoolYear> listYear = null;
        List<TimeTableWeek> listWeek = null;
        Integer yearIdCurrent = null;
        Integer weekIdCurrent = null;
        Date date = Date.valueOf(LocalDate.now());

        listYear = schoolYear.findAllSortByFromDate();
        if(listYear.size() != 0){
            list.setListYear(listYear);
            for (int i = 0; i < listYear.size(); i++) {
                SchoolYear schoolYear = listYear.get(i);
                //find yearIdCurrent
                if (date.compareTo(schoolYear.getFromDate()) > 0 && date.compareTo(schoolYear.getToDate()) < 0) {
                    yearIdCurrent = schoolYear.getYearID();
                }
            }
            //check yearIdCurrent null or not
            if (yearIdCurrent == null && listYear.size() != 0) {
                yearIdCurrent = listYear.get(0).getYearID();
            }
            list.setYearIdCurrent(yearIdCurrent);
        }

        listWeek = timeTableWeekRepository.findByYearIdANdSortByFromDate(yearIdCurrent);

        //check listWeek null or not
        if (listWeek.size() != 0) {
            list.setListWeek(listWeek);
            for (int i = 0; i < listWeek.size(); i++) {
                TimeTableWeek timeTableWeek = listWeek.get(i);
                //find weekIdCurrent
                if (date.compareTo(timeTableWeek.getFromDate()) > 0 && date.compareTo(timeTableWeek.getToDate()) < 0) {
                    weekIdCurrent = timeTableWeek.getTimeTableWeekId();
                    break;
                }
            }

            //check weekIdCurrent null or not
            if (weekIdCurrent == null) {
                weekIdCurrent = listWeek.get(0).getTimeTableWeekId();
            }
            list.setWeekIdCurrent(weekIdCurrent);
        }
        return  list;
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
            listAfternoon.add(afternoonTimeTable);
        }
        return  listAfternoon;
    }
}
