package com.finalproject.schedule.Modules.Course.controller;

import com.finalproject.schedule.Modules.Course.model.Course;
import com.finalproject.schedule.Modules.Course.service.CourseService;
import com.finalproject.schedule.Modules.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.security.Principal;

@Controller
@RequestMapping("/Courses")
public class CourseController {

    private CourseService courseService;
    private UserService userService;
    
    @Autowired
    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String admin_course(Model model, Principal principal) {
        model.addAttribute("new_course", new Course()); /* used in form to add new Course */
        model.addAttribute("course_model", courseService.findAllCourses()); /* used in table to to show Course Information */
        model.addAttribute("profile", userService.findByEmail(principal.getName())); /* used in navbar to to show User Profile */
        return "admin/admin_course";
    }

    @RequestMapping(value = "/addcourse", method = RequestMethod.POST)
    public String addCourse(@ModelAttribute(name = "course") Course course){
        courseService.addCourse(course); /* admin add course */
        return "redirect:/Courses";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id) {
        courseService.deleteById(id); /* admin delete course */
        return "redirect:/Courses";
    }

}
