package com.example.webDemo3.ServiceTest;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.manageTeacherRequestDto.AddTeacherRequestDto;
import com.example.webDemo3.dto.request.manageTeacherRequestDto.DeleteTeacherRequestDto;
import com.example.webDemo3.entity.*;
import com.example.webDemo3.repository.TeacherRepository;
import com.example.webDemo3.service.manageTeacherService.TeacherService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * lamnt98
 * 19/07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherServiceTest {

    @MockBean
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherService teacherService;

    @Before
    public void setUp() {

        Teacher teacher = new Teacher();
        teacher.setTeacherIdentifier("lam");
        teacher.setPhone("0348521119");
        teacher.setEmail("lamntse05790@fpt.edu.vn");
        teacher.setTeacherId(1);
        teacher.setFullName("Nguyen Tung Lam");
        teacher.setStatus(1);

        Teacher teacher2 = new Teacher();
        teacher2.setTeacherIdentifier("yen");
        teacher2.setPhone("0348521119");
        teacher2.setEmail("lamntse05790@fpt.edu.vn");
        teacher2.setTeacherId(2);
        teacher2.setFullName("Vu Bao Yen");
        teacher2.setStatus(1);

        Mockito.when(teacherRepository.findTeacherTeacherIdentifier("lam")).thenReturn(teacher);
    }

    /**
     * Test add teacher service successfully
     * @throws
     */
    @Test
    public void addTeacherSuccess() {
        MessageDTO message = new MessageDTO();
        message = Constant.ADD_TEACHER_SUCCESS;

        AddTeacherRequestDto requestDto = new AddTeacherRequestDto();
        requestDto.setTeacherIdentifier("kim");
        requestDto.setPhone("0125632598");
        requestDto.setEmail("kimptse02563@fpt.edu.vn");
        requestDto.setFullName("Pham Trong Kim");

        Assert.assertEquals(message, teacherService.addTeacher(requestDto));
    }

    /**
     * Test add teacher service with teacherIdentifier is empty
     * @throws
     */
    @Test
    public void testTeacherIdentifierEmpty() {
        MessageDTO message = new MessageDTO();
        message = Constant.TEACHER_IDENTIFIER_EMPTY;

        AddTeacherRequestDto requestDto = new AddTeacherRequestDto();
        requestDto.setTeacherIdentifier("");
        requestDto.setPhone("0125632598");
        requestDto.setEmail("kimptse02563@fpt.edu.vn");
        requestDto.setFullName("Pham Trong Kim");

        Assert.assertEquals(message, teacherService.addTeacher(requestDto));
    }

    /**
     * Test add teacher service with teacherIdentifier is empty
     * @throws
     */
    @Test
    public void testTeacherExists() {
        MessageDTO message = new MessageDTO();
        message = Constant.TEACHER_EXIT;

        AddTeacherRequestDto requestDto = new AddTeacherRequestDto();
        requestDto.setTeacherIdentifier("lam");
        requestDto.setPhone("0125632598");
        requestDto.setEmail("kimptse02563@fpt.edu.vn");
        requestDto.setFullName("Pham Trong Kim");

        Assert.assertEquals(message, teacherService.addTeacher(requestDto));
    }

    /**
     * Test delete teacher service successfully
     * @throws
     */
    @Test
    public void testDeleteTeacherSuccess() {
        MessageDTO message = new MessageDTO();
        message = Constant.DELETE_TEACHER_SUCCESS;

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        DeleteTeacherRequestDto requestDto = new DeleteTeacherRequestDto();
        requestDto.setListTeacher(list);

        Assert.assertEquals(message, teacherService.deleteTeacher(requestDto));
    }

    /**
     * Test delete teacher service with list teacher is empty
     * @throws
     */
    @Test
    public void testListTeacherEmpty() {
        MessageDTO message = new MessageDTO();
        message = Constant.TEACHER_NOT_EXIT;

        List<Integer> list = new ArrayList<>();
        DeleteTeacherRequestDto requestDto = new DeleteTeacherRequestDto();
        requestDto.setListTeacher(list);

        Assert.assertEquals(message, teacherService.deleteTeacher(requestDto));
    }

    /**
     * Test delete teacher service with transaction
     * @throws
     */
    @Test
    public void testListTeacherWithErrorTransaction() {
        MessageDTO message = new MessageDTO();
        message = Constant.TEACHER_NOT_EXIT;

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(123);
        DeleteTeacherRequestDto requestDto = new DeleteTeacherRequestDto();
        requestDto.setListTeacher(list);

        Assert.assertEquals(message, teacherService.deleteTeacher(requestDto));
    }
}
