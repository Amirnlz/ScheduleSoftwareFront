package com.finalproject.schedule.Modules.TimeTableBell.controller;

import com.finalproject.schedule.Modules.Bell.service.BellService;
import com.finalproject.schedule.Modules.Day.service.DayService;
import com.finalproject.schedule.Modules.TimeTableBell.model.TimeTableBell;
import com.finalproject.schedule.Modules.TimeTableBell.service.TimeTableBellService;
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


@Controller
@RequestMapping("/TimeTableBell")
public class TimeTableBellController {

    private TimeTableBellService timeTableBellService;
    private DayService dayService;
    private BellService bellService;
    private UserService userService;

    @Autowired
    public TimeTableBellController(TimeTableBellService timeTableBellService, DayService dayService, BellService bellService, UserService userService) {
        this.timeTableBellService = timeTableBellService;
        this.dayService = dayService;
        this.bellService = bellService;
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String timetabelbell(Model model, Principal principal){
        model.addAttribute("new_timetablebell", new TimeTableBell());
        model.addAttribute("day_model",dayService.findAllDays());
        model.addAttribute("bell_model",bellService.findAllBells());
        model.addAttribute("timetablebell_model",timeTableBellService.findAllTimeTableBell());
        model.addAttribute("profile", userService.findByEmail(principal.getName()));
        return "admin/admin_timetablebell";
    }

    @RequestMapping(value = "/addtimetablebell", method = RequestMethod.POST)
    public String addtimetablebell(@ModelAttribute(name = "timetabelbell") TimeTableBell timetablebell) throws IOException, InvocationTargetException, IllegalAccessException {
        timeTableBellService.addTimeTableBell(timetablebell);
        return "redirect:/TimeTableBell";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id) {
        timeTableBellService.deleteById(id);
        return "redirect:/TimeTableBell";
    }

}
