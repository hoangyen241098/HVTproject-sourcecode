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
        return "manageAccount/forgotPassword";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "manageAccount/login";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model) {
        return "manageAccount/changePassword";
    }

    @GetMapping("/manageAccount")
    public String manageAccount(Model model) {
        return "manageAccount/manageAccount";
    }

    @GetMapping("/createAccount")
    public String createAccount(Model model) {
        return "manageAccount/createAccount";
    }

    @GetMapping("/userInformation")
    public String userInformation(Model model) {
        return "manageAccount/userInformation";
    }

    /*Module 2: Quản lý giáo viên*/
    @GetMapping("/manageTeacher")
    public String manageTeacher(Model model) {
        return "manageTeacher/manageTeacher";
    }

    @GetMapping("/createTeacher")
    public String createTeacher(Model model) {
        return "manageTeacher/createTeacher";
    }

    @GetMapping("/teacherInformation")
    public String teacherInformation(Model model) {
        return "manageTeacher/teacherInformation";
    }

    /*Module 3: Quản lý lớp*/
    @GetMapping("/manageClass")
    public String manageClass(Model model) {
        return "manageClass/manageClass";
    }

    @GetMapping("/createClass")
    public String createClass(Model model) {
        return "manageClass/createClass";
    }

    @GetMapping("/editClass")
    public String editClass(Model model) {
        return "manageClass/editClass";
    }

    @GetMapping("/createGifftedClass")
    public String createGifftedClass(Model model) {
        return "manageClass/createGifftedClass";
    }

    @GetMapping("/deleteGifftedClass")
    public String deleteGifftedClass(Model model) {
        return "manageClass/deleteGifftedClass";
    }

    /*Module 4: Thời khóa biểu*/
    @GetMapping("/timetableClass")
    public String timetableClass(Model model) {
        return "timetable/timetableClass";
    }

    @GetMapping("/timetableTeacher")
    public String timetableTeacher(Model model) {
        return "timetable/timetableTeacher";
    }

    /*Module 5: Quản lý lỗi*/
    @GetMapping("/violationList")
    public String violationList(Model model) {
        return "manageViolation/violationList";
    }

    @GetMapping("/editViolation")
    public String editViolation(Model model) {
        return "manageViolation/editViolation";
    }

    @GetMapping("/addViolation")
    public String addViolation(Model model) {
        return "manageViolation/addViolation";
    }

    @GetMapping("/editViolationType")
    public String editViolationType(Model model) {
        return "manageViolation/editViolationType";
    }

    @GetMapping("/addViolationType")
    public String addViolationType(Model model) {
        return "manageViolation/addViolationType";
    }

    /*Module 6: Quản lý năm học*/
    @GetMapping("/schoolYearList")
    public String schoolYearList(Model model) {
        return "schoolYear/schoolYearList";
    }

    @GetMapping("/addSchoolYear")
    public String addSchoolYear(Model model) {
        return "schoolYear/addSchoolYear";
    }

    @GetMapping("/editSchoolYear")
    public String editSchoolYear(Model model) {
        return "schoolYear/editSchoolYear";
    }

    /*Module 7: Trực tuần*/
    @GetMapping("/assignEmulation")
    public String assignWeekly(Model model) {
        return "manageEmulation/assignEmulation";
    }

    @GetMapping("/gradingToEmulation")
    public String gradingToEmulation(Model model) {
        return "manageEmulation/gradingToEmulation";
    }

    @GetMapping("/violationListOfClass")
    public String violationListOfClass(Model model) {
        return "manageEmulation/violationListOfClass";
    }

    @GetMapping("/addNewViolation")
    public String addNewViolation(Model model) {
        return "manageEmulation/addNewViolation";
    }

    /*Module 8: Quản lý thời gian*/
    @GetMapping("/manageViolationEnteringTime")
    public String manageViolationEnteringTime(Model model) {
        return "manageViolationEnteringTime/manageViolationEnteringTime";
    }

    @GetMapping("/createViolationEnteringTime")
    public String createViolationEnteringTime(Model model) {
        return "manageViolationEnteringTime/createViolationEnteringTime";
    }

    /*Module 9: Quản lý xếp hạng*/
    @GetMapping("/rankByWeek")
    public String rankByWeek(Model model) {
        return "manageSchoolRank/rankByWeek";
    }

    @GetMapping("/rankByMonth")
    public String rankByMonth(Model model) {
        return "manageSchoolRank/rankByMonth";
    }

    @GetMapping("/rankBySemester")
    public String rankBySemester(Model model) {
        return "manageSchoolRank/rankBySemester";
    }

    @GetMapping("/rankByYear")
    public String rankByYear(Model model) {
        return "manageSchoolRank/rankByYear";
    }
}