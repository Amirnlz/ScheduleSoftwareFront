package com.finalproject.schedule.Modules.User.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.finalproject.schedule.Modules.User.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.finalproject.schedule.Modules.User.model.User;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;

@Controller
@RequestMapping("/Users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String admin_user(Model model, Principal principal, @PageableDefault(size = 20) Pageable pageable){
        model.addAttribute("new_user", new User()); /* used in form to add new User */
        model.addAttribute("user_model", userService.findAllUsers(pageable)); /* used in table to to show User Information */
        model.addAttribute("profile", userService.findByCode(principal.getName())); /* used in navbar to to show User Profile */
        return "admin/admin_user";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute User user) throws IOException, InvocationTargetException, IllegalAccessException {
        userService.registerUser(user); /* register user with image */
        return "redirect:/Users";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id) {
        userService.deleteById(id); /* delete user by id */
        return "redirect:/Users";
    }

}
