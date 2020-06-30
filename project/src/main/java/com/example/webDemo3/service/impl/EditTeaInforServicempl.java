package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.EditTeaInforRequestDto;
import com.example.webDemo3.entity.Teacher;
import com.example.webDemo3.repository.TeacherRepository;
import com.example.webDemo3.service.EditTeaInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * lamt98
 * 29/06
 */
@Service
public class EditTeaInforServicempl implements EditTeaInforService {

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public MessageDTO editTeacherInformation(EditTeaInforRequestDto editTeaInforRequestDto) {
        MessageDTO message = new MessageDTO();
        Teacher teacher = null;
        Teacher newTeacher = null;

        try {
            Integer teacherId = editTeaInforRequestDto.getTeacherId();

            //check teacherId empty or not
            if(teacherId.toString().isEmpty()){
                message = Constant.TEACHER_NOT_EXIT;
                return message;
            }

            //check teacherIdentifier empty or not
            if(editTeaInforRequestDto.getTeacherIdentifier().trim().isEmpty()){
                message = Constant.TEACHER_IDENTIFIER_EMPTY;
                return message;
            }

            //check and find teacher in database
            teacher = teacherRepository.findById(teacherId).orElse(null);
            if(teacher != null)
            {
                //check teacherIdentifier exist or not
                newTeacher = teacherRepository.findTeacherTeacherIdentifier(editTeaInforRequestDto.getTeacherIdentifier());
                if(newTeacher != null && newTeacher.getTeacherId() != teacherId){
                    message = Constant.TEACHER_EXIT;
                    return message;
                }
                teacher.setFullName(editTeaInforRequestDto.getFullName());
                teacher.setPhone(editTeaInforRequestDto.getPhone());
                teacher.setEmail(editTeaInforRequestDto.getEmail());
                teacher.setTeacherIdentifier(editTeaInforRequestDto.getTeacherIdentifier());
                teacherRepository.save(teacher);
                message = Constant.EDIT_TEACHER_INFORMATION_SUCCESS;
            }else{
                message = Constant.TEACHER_NOT_EXIT;
            }
        } catch (Exception e) {
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }
        return message;
    }
}
