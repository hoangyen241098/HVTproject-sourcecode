package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.DeleteTeacherRequestDto;
import com.example.webDemo3.entity.Teacher;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.TeacherRepository;
import com.example.webDemo3.service.DeleteTeacherService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * lamnt98
 * 30/06
 */
@Service
public class DeleteTeacherServicempl implements DeleteTeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class})
    public MessageDTO deleteTeacher(DeleteTeacherRequestDto deleteTeacher) {
        MessageDTO messageDTO = new MessageDTO();
        try{
            List<Integer> listTeacher = deleteTeacher.getListTeacher();
            for(Integer teacherId : listTeacher){
                Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
                //check teacher exists or not
                if(teacher != null && (teacher.getStatus() == null || teacher.getStatus() != 1)){
                    teacher.setStatus(1);
                    teacherRepository.save(teacher);
                }else{
                    messageDTO = Constant.TEACHER_NOT_EXIT;
                    return  messageDTO;
                }
            }
            messageDTO = Constant.DELETE_TEACHER_SUCCESS;
        }catch (Exception e){
            messageDTO.setMessageCode(1);
            messageDTO.setMessage(e.toString());
            return messageDTO;
        }
        return messageDTO;
    }
}
