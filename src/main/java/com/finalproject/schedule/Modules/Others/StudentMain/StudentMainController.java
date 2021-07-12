package com.finalproject.schedule.Modules.Others.StudentMain;

import com.finalproject.schedule.Modules.Announcements.model.Announce;
import com.finalproject.schedule.Modules.Announcements.service.AnnounceService;
import com.finalproject.schedule.Modules.Bell.model.Bell;
import com.finalproject.schedule.Modules.Bell.service.BellService;
import com.finalproject.schedule.Modules.Day.service.DayService;
import com.finalproject.schedule.Modules.Student.model.StudentCourse;
import com.finalproject.schedule.Modules.Student.service.StudentCourseService;
import com.finalproject.schedule.Modules.TimeTable.model.TimeTable;
import com.finalproject.schedule.Modules.TimeTable.service.TimeTableService;
import com.finalproject.schedule.Modules.TimeTableBell.service.TimeTableBellService;
import com.finalproject.schedule.Modules.User.model.User;
import com.finalproject.schedule.Modules.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentMainController {

    private UserService userService;
    private DayService dayService;
    private BellService bellService;
    private AnnounceService announceService;
    private TimeTableBellService timetablebellService;
    private TimeTableService timetableService;
    private StudentCourseService studentcourseService;

    @Autowired
    public StudentMainController(UserService userService, DayService dayService, BellService bellService, AnnounceService announceService, TimeTableBellService timetablebellService, TimeTableService timetableService, StudentCourseService studentcourseService) {
        this.userService = userService;
        this.dayService = dayService;
        this.bellService = bellService;
        this.announceService = announceService;
        this.timetablebellService = timetablebellService;
        this.timetableService = timetableService;
        this.studentcourseService = studentcourseService;
    }

    @RequestMapping(value = "/student_main")
    public String student_main(Model model, Principal principal){
        model.addAttribute("profile", userService.findByCode(principal.getName()));

        User user = userService.findByCode(principal.getName());
        List<StudentCourse> studentcourseList = studentcourseService.findAllStudentCourses();
        List<StudentCourse> temp = new ArrayList<>();
        for(StudentCourse studentcourse : studentcourseList){
            if(studentcourse.getUser().equals(user)){
                temp.add(studentcourse);
            }
        }
        model.addAttribute("student_choose_model", temp);

        List<Announce> announceList = announceService.findAllAnnounce();
        List<Announce> announcetemp = new ArrayList<>();
        for(int i=0; i<announceList.size(); i++){
            for (int j=0; j<temp.size(); j++){
                if(announceList.get(i).getTimeTable().equals(temp.get(j).getTimetable())){
                    announcetemp.add(announceList.get(i));
                }
            }
        }
        model.addAttribute("announce_model", announcetemp);
        return "student/student_main";
    }

}
