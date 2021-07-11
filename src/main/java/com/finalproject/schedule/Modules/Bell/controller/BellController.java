package com.finalproject.schedule.Modules.Bell.controller;

import com.finalproject.schedule.Modules.Bell.model.Bell;
import com.finalproject.schedule.Modules.Bell.service.BellService;
import com.finalproject.schedule.Modules.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;

@Controller
@RequestMapping("/Bells")
public class BellController {

    private BellService bellService;
    private UserService userService;

    @Autowired
    public BellController(BellService bellService, UserService userService) {
        this.bellService = bellService;
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String bell(Model model, Principal principal){
        model.addAttribute("new_bell", new Bell()); /* used in form to add new Bell */
        model.addAttribute("bell_model", bellService.findAllBells()); /* used in table to to show Label and bellOfDay */
        model.addAttribute("profile", userService.findByEmail(principal.getName()));
        return "admin/admin_bell";
    }

    @RequestMapping(value = "/addBell", method = RequestMethod.POST)
    public String addBell(@ModelAttribute(name = "bell") Bell bell) throws IOException, InvocationTargetException, IllegalAccessException {
        bellService.addBell(bell);
        return "redirect:/Bells";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id) {
        bellService.deleteById(id);
        return "redirect:/Bells";
    }

}


