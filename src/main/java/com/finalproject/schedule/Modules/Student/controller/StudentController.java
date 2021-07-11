package com.finalproject.schedule.Modules.Student.controller;

import com.finalproject.schedule.Modules.Student.model.StudentCourse;
import com.finalproject.schedule.Modules.Student.service.StudentCourseService;
import com.finalproject.schedule.Modules.TimeTable.model.TimeTable;
import com.finalproject.schedule.Modules.TimeTable.service.TimeTableService;
import com.finalproject.schedule.Modules.User.model.User;
import com.finalproject.schedule.Modules.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

    private UserService userService;
    private TimeTableService timetableService;
    private StudentCourseService studentcourseService;

    @Autowired
    public StudentController(UserService userService, TimeTableService timetableService, StudentCourseService studentcourseService) {
        this.userService = userService;
        this.timetableService = timetableService;
        this.studentcourseService = studentcourseService;
    }

    @RequestMapping(value = "/student_main")
    public String student_main(Model model, Principal principal){
        model.addAttribute("profile", userService.findByEmail(principal.getName()));

        return "student/student_main";
    }

    @RequestMapping(value = "/student_choose", method = RequestMethod.GET)
    public String student_choose(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("student_choose", new StudentCourse());
        List<StudentCourse> studentcourseList = studentcourseService.findAllStudentCourses();
        List<StudentCourse> temp = new ArrayList<>();
        for (StudentCourse studentcourse: studentcourseList){
            if (studentcourse.getUser().equals(user)){
                temp.add(studentcourse);
            }
        }
        model.addAttribute("student_choose_model",temp);

        List<TimeTable> timetableList2 = timetableService.findAllTimeTables();
        List<TimeTable> temp2 = new ArrayList<>();
        for (TimeTable timetable: timetableList2){
            if (timetable.getAcceptance() == 1){
                temp2.add(timetable);
            }
        }
        model.addAttribute("timetable_model",temp2);
        model.addAttribute("profile", userService.findByEmail(principal.getName()));
        return "student/student_course";
    }

    @RequestMapping(value = "/student_choose/addcourse", method = RequestMethod.POST)
    public String addcourse(@ModelAttribute(name = "course") StudentCourse studentCourse, Principal principal) throws IOException, InvocationTargetException, IllegalAccessException {
        studentCourse.setUser(userService.findByEmail(principal.getName()));
        studentcourseService.addCourse(studentCourse);
        return "redirect:/student_choose";
    }

    @RequestMapping(value = {"/student_choose/delete/{id}"}, method = RequestMethod.GET)
    public String master_delete(@PathVariable("id") int id) {
        studentcourseService.deleteById(id);
        return "redirect:/student_choose";
    }

}
