package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.AddTeacherRequestDto;
import com.example.webDemo3.entity.Teacher;
import com.example.webDemo3.repository.TeacherRepository;
import com.example.webDemo3.service.AddTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddTeacherServicempl implements AddTeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public MessageDTO addTeacher(AddTeacherRequestDto addTeacher) {
        MessageDTO messageDTO = new MessageDTO();
        Teacher teacher = new Teacher();
        String teacherIdentifier = addTeacher.getTeacherIdentifier().trim();
        String fullName = addTeacher.getFullName();
        String phone = addTeacher.getPhone();
        String email = addTeacher.getEmail();

        try {
            if (teacherIdentifier.isEmpty()) {
                messageDTO = Constant.TEACHER_IDENTIFIER_EMPTY;
                return messageDTO;
            }
            teacher = teacherRepository.findTeacherTeacherIdentifier(teacherIdentifier);
            if (teacher == null) {
                teacher = new Teacher();
                teacher.setFullName(fullName);
                teacher.setTeacherIdentifier(teacherIdentifier);

                teacher.setEmail(email);
                teacher.setPhone(phone);
                teacherRepository.save(teacher);
            } else {
                messageDTO = Constant.TEACHER_EXIT;
                return messageDTO;
            }
            messageDTO = Constant.ADD_TEACHER_SUCCESS;
        }catch (Exception e){
            messageDTO.setMessageCode(1);
            messageDTO.setMessage(e.toString());
            return messageDTO;
        }
        return messageDTO;
    }
}
