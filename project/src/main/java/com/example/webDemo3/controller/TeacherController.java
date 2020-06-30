package com.example.webDemo3.controller;

import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.ViewTeaListResponseDto;
import com.example.webDemo3.dto.request.AddTeacherRequestDto;
import com.example.webDemo3.dto.request.DeleteTeacherRequestDto;
import com.example.webDemo3.dto.request.EditTeaInforRequestDto;
import com.example.webDemo3.dto.request.ViewTeaListResquestDto;
import com.example.webDemo3.service.AddTeacherService;
import com.example.webDemo3.service.DeleteTeacherService;
import com.example.webDemo3.service.EditTeaInforService;
import com.example.webDemo3.service.ViewTeaListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class TeacherController {

    @Autowired
    private EditTeaInforService editTeaInforService;

    @Autowired
    private DeleteTeacherService deleteTeacherService;

    @Autowired
    private AddTeacherService addTeacherService;

    @Autowired
    private ViewTeaListService viewTeaListService;

    /**
     * lamnt98
     * 30/06
     * catch request from client to update teacher information
     * @param model
     * @return MessageDTO
     */
    @PostMapping("/editteacherinformation")
    public MessageDTO editTeacherInfor(@RequestBody EditTeaInforRequestDto model)
    {
        return editTeaInforService.editTeacherInformation(model);
    }

    /**
     * lamnt98
     * 30/06
     * catch request from client to delete teacher
     * @param model
     * @return MessageDTO
     */
    @PostMapping("/deleteteacher")
    public MessageDTO deleteTeacher(@RequestBody DeleteTeacherRequestDto model)
    {
        return deleteTeacherService.deleteTeacher(model);
    }

    /**
     * lamnt98
     * 30/06
     * catch request from client to add teacher
     * @param model
     * @return MessageDTO
     */
    @PostMapping("/addteacher")
    public MessageDTO addTeacher(@RequestBody AddTeacherRequestDto model)
    {
        return addTeacherService.addTeacher(model);
    }

    /**
     * lamnt98
     * 30/06
     * catch request from client to search teacher
     * @param model
     * @return ViewTeaListResponseDto
     */
    @PostMapping("/teacherlist")
    public ViewTeaListResponseDto addTeacher(@RequestBody ViewTeaListResquestDto model)
    {
        return viewTeaListService.searchTeacher(model);
    }
}
