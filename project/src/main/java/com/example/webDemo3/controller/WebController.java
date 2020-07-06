package com.example.webDemo3.controller;

import com.example.webDemo3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/") // Nếu người dùng request tới địa chỉ "/"
    public String index(Model model) {
//        model.addAttribute("userList", userRepository.findAll());
        return "index"; // Trả về file index.html
    }

    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }

    /*Module 1: Quản lý tài khoản*/
    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model) {
        return "forgotPassword";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model) {
        return "changePassword";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(Model model) {
        return "resetPassword";
    }

    @GetMapping("/manageAccount")
    public String manageAccount(Model model) {
        return "manageAccount";
    }

    @GetMapping("/createAccount")
    public String createAccount(Model model) {
        return "createAccount";
    }

    @GetMapping("/userInformation")
    public String userInformation(Model model) {
        return "userInformation";
    }

    /*Module 2: Quản lý giáo viên*/
    @GetMapping("/manageTeacher")
    public String manageTeacher(Model model) {
        return "manageTeacher";
    }

    @GetMapping("/createTeacher")
    public String createTeacher(Model model) {
        return "createTeacher";
    }

    @GetMapping("/teacherInformation")
    public String teacherInformation(Model model) {
        return "teacherInformation";
    }

    /*Module 3: Quản lý lớp*/
    @GetMapping("/manageClass")
    public String manageClass(Model model) {
        return "manageClass";
    }

    @GetMapping("/createClass")
    public String createClass(Model model) {
        return "createClass";
    }

    @GetMapping("/editClass")
    public String editClass(Model model) {
        return "editClass";
    }

    @GetMapping("/createGifftedClass")
    public String createGifftedClass(Model model) {
        return "createGifftedClass";
    }

    @GetMapping("/deleteGifftedClass")
    public String deleteGifftedClass(Model model) {
        return "deleteGifftedClass";
    }

    /*Module 4: Thời khóa biểu*/
    @GetMapping("/timetableClass")
    public String timetableClass(Model model) {
        return "timetableClass";
    }

    @GetMapping("/timetableTeacher")
    public String timetableTeacher(Model model) {
        return "timetableTeacher";
    }

    /*Module 5: Quản lý lỗi*/
    @GetMapping("/violationList")
    public String violationList(Model model) {
        return "violationList";
    }

    @GetMapping("/editViolation")
    public String editViolation(Model model) {
        return "editViolation";
    }

    @GetMapping("/addViolation")
    public String addViolation(Model model) {
        return "addViolation";
    }

    @GetMapping("/editViolationType")
    public String editViolationType(Model model) {
        return "editViolationType";
    }

    @GetMapping("/addViolationType")
    public String addViolationType(Model model) {
        return "addViolationType";
    }

    /*Module 6: Quản lý năm học*/
    @GetMapping("/schoolYearList")
    public String schoolYearList(Model model) {
        return "schoolYearList";
    }

    @GetMapping("/addSchoolYear")
    public String addSchoolYear(Model model) {
        return "addSchoolYear";
    }

    @GetMapping("/editSchoolYear")
    public String editSchoolYear(Model model) {
        return "editSchoolYear";
    }

    /*Module 7: Trực tuần*/
    @GetMapping("/assignWeekly")
    public String assignWeekly(Model model) {
        return "assignWeekly";
    }

    @GetMapping("/gradingToEmulation")
    public String gradingToEmulation(Model model) {
        return "gradingToEmulation";
    }

    @GetMapping("/violationListOfClass")
    public String violationListOfClass(Model model) {
        return "violationListOfClass";
    }

    @GetMapping("/addNewViolation")
    public String addNewViolation(Model model) {
        return "addNewViolation";
    }
}