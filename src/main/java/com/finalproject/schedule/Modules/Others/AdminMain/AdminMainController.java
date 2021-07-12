package com.finalproject.schedule.Modules.Others.AdminMain;

import com.finalproject.schedule.Modules.Announcements.service.AnnounceService;
import com.finalproject.schedule.Modules.Bell.service.BellService;
import com.finalproject.schedule.Modules.Day.service.DayService;
import com.finalproject.schedule.Modules.Student.service.StudentCourseService;
import com.finalproject.schedule.Modules.TimeTable.model.TimeTable;
import com.finalproject.schedule.Modules.TimeTable.service.TimeTableService;
import com.finalproject.schedule.Modules.TimeTableBell.service.TimeTableBellService;
import com.finalproject.schedule.Modules.User.model.User;
import com.finalproject.schedule.Modules.User.service.UserService;
import com.finalproject.schedule.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminMainController {

    private UserService userService;
    private DayService dayService;
    private BellService bellService;
    private AnnounceService announceService;
    private TimeTableBellService timetablebellService;
    private TimeTableService timetableService;
    private StudentCourseService studentcourseService;

    @Autowired
    public AdminMainController(UserService userService, DayService dayService, BellService bellService, AnnounceService announceService, TimeTableBellService timetablebellService, TimeTableService timetableService, StudentCourseService studentcourseService) {
        this.userService = userService;
        this.dayService = dayService;
        this.bellService = bellService;
        this.announceService = announceService;
        this.timetablebellService = timetablebellService;
        this.timetableService = timetableService;
        this.studentcourseService = studentcourseService;
    }

    @RequestMapping(value = "/admin_main")
    public String admin_main(Model model, Principal principal) {
        model.addAttribute("profile", userService.findByCode(principal.getName()));

        model.addAttribute("master_length", userService.findByRoles(Roles.MASTER.toString()).size());
        model.addAttribute("admin_length", userService.findByRoles(Roles.ADMIN.toString()).size());
        model.addAttribute("student_length", userService.findByRoles(Roles.STUDENT.toString()).size());

        model.addAttribute("bell_num_in_shanbe", timetablebellService.findBellnumber("شنبه").size());
        model.addAttribute("bell_num_in_yekshanbe", timetablebellService.findBellnumber("یکشنبه").size());
        model.addAttribute("bell_num_in_doshanbe", timetablebellService.findBellnumber("دوشنبه").size());
        model.addAttribute("bell_num_in_seshanbe", timetablebellService.findBellnumber("سه شنبه").size());
        model.addAttribute("bell_num_in_charshanbe", timetablebellService.findBellnumber("چهارشنبه").size());
        model.addAttribute("bell_num_in_panjshanbe", timetablebellService.findBellnumber("پنجشنبه").size());
        model.addAttribute("bell_num_in_jome", timetablebellService.findBellnumber("جمعه").size());

        model.addAttribute("masters_in_shanbe", timetableService.findtimetablenumber("شنبه").size());
        model.addAttribute("masters_in_yekshanbe", timetableService.findtimetablenumber("یکشنبه").size());
        model.addAttribute("masters_in_doshanbe", timetableService.findtimetablenumber("دوشنبه").size());
        model.addAttribute("masters_in_seshanbe", timetableService.findtimetablenumber("سه شنبه").size());
        model.addAttribute("masters_in_charshanbe", timetableService.findtimetablenumber("چهارشنبه").size());
        model.addAttribute("masters_in_panjshanbe", timetableService.findtimetablenumber("پنجشنبه").size());
        model.addAttribute("masters_in_jome", timetableService.findtimetablenumber("جمعه").size());

        model.addAttribute("students_in_shanbe", studentcourseService.findtimetablenumber("شنبه").size());
        model.addAttribute("students_in_yekshanbe", studentcourseService.findtimetablenumber("یکشنبه").size());
        model.addAttribute("students_in_doshanbe", studentcourseService.findtimetablenumber("دوشنبه").size());
        model.addAttribute("students_in_seshanbe", studentcourseService.findtimetablenumber("سه شنبه").size());
        model.addAttribute("students_in_charshanbe", studentcourseService.findtimetablenumber("چهارشنبه").size());
        model.addAttribute("students_in_panjshanbe", studentcourseService.findtimetablenumber("پنجشنبه").size());
        model.addAttribute("students_in_jome", studentcourseService.findtimetablenumber("جمعه").size());

        model.addAttribute("studentcourseService_model",studentcourseService);
        model.addAttribute("announce_model",announceService.findAllAnnounce());

        return "admin/admin_main";
    }

    @RequestMapping(value = "/admin_main_announcements/delete/{id}", method = RequestMethod.GET)
    public String admin_main_delete(@PathVariable("id") int id) {
        announceService.deleteById(id);
        return "redirect:/admin_main";
    }

}
