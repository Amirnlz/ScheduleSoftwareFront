package com.finalproject.schedule.Modules.Master.controller;

import com.finalproject.schedule.Modules.Bell.service.BellService;
import com.finalproject.schedule.Modules.Course.service.CourseService;
import com.finalproject.schedule.Modules.Day.service.DayService;
import com.finalproject.schedule.Modules.Master.model.MasterCourse;
import com.finalproject.schedule.Modules.Master.service.MasterCourseService;
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
public class MasterController {

    private DayService dayService;
    private BellService bellService;
    private CourseService courseService;
    private MasterCourseService mastercourseService;
    private UserService userService;

    @Autowired
    public MasterController(DayService dayService, BellService bellService, CourseService courseService, MasterCourseService mastercourseService, UserService userService) {
        this.dayService = dayService;
        this.bellService = bellService;
        this.courseService = courseService;
        this.mastercourseService = mastercourseService;
        this.userService = userService;
    }

    @RequestMapping(value = "/master_main")
    public String master_main(Model model, Principal principal){
        model.addAttribute("profile", userService.findByEmail(principal.getName()));
        return "master/master_main";
    }

    @RequestMapping(value = "/master_course", method = RequestMethod.GET)
    public String course(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("master_course", new MasterCourse()); /* used in form to add new MasterCourse */
        model.addAttribute("course_model",courseService.findAllCourses()); /* used in comboBox to to show all Courses */
        List<MasterCourse> mastercourseList = mastercourseService.findAllMasterCourses();
        List<MasterCourse> temp = new ArrayList<>();
        for (MasterCourse mastercourse: mastercourseList){
            if (mastercourse.getUser().equals(user)){
                temp.add(mastercourse);
            }
        }
        model.addAttribute("master_course_model",temp); /* used in table to to show selected Courses */
        model.addAttribute("profile", userService.findByEmail(principal.getName()));
        return "master/master_course";
    }

    @RequestMapping(value = "/master_course/addcourse", method = RequestMethod.POST)
    public String addcourse(@ModelAttribute(name = "course") MasterCourse mastercourse, Principal principal) throws IOException, InvocationTargetException, IllegalAccessException {
        mastercourse.setUser(userService.findByEmail(principal.getName()));
        mastercourseService.addMasterCourse(mastercourse);
        return "redirect:/master_course";
    }

    @RequestMapping(value = "/master_course/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id) {
        mastercourseService.deleteById(id);
        return "redirect:/master_course";
    }






}
