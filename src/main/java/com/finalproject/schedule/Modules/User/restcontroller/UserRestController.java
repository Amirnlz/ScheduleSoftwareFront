package com.finalproject.schedule.Modules.User.restcontroller;

import com.finalproject.schedule.Modules.User.model.PageModel;
import com.finalproject.schedule.Modules.User.model.User;
import com.finalproject.schedule.Modules.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Users")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    /*
    http://localhost:8585/api/Users/Add
    POST
    */
    @RequestMapping(value = "/Add", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    /*
    http://localhost:8585/api/Users/AddList
    POST
    */
    @RequestMapping(value = "/AddList", method = RequestMethod.POST)
    public List<User> registerUserList(@RequestBody List<User> user){
        return userService.saveUserList(user);
    }

    /*
    http://localhost:8085/api/Users
    GET
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.findAllUsers();
    }
    */

    /*
    http://localhost:8585/api/Users/id
    GET
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User findUserById(@PathVariable("id")int id){
        return userService.findById(id);
    }

    /*
    http://localhost:8585/api/Users/id
    PUT
    */
    @PutMapping(value = "/{id}")
    public User update(@RequestBody User user){
        return userService.saveUser(user);
    }

    /*
    http://localhost:8585/api/Users/id
    DELETE
    */
    @DeleteMapping(value = "/{id}")
    public void deletebyId(@PathVariable int id){
        userService.deleteById(id);
    }

    /*
    http://localhost:8585/api/Users/Profile
    GET
    */
    @RequestMapping(value = "/Profile", method = RequestMethod.GET)
    public User getUserProfile() {
        return userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /*
    http://localhost:8585/api/Users
    GET
    */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageModel getUsersPage(@RequestParam int pageNumber,@RequestParam int pageSie,@RequestParam String name,@RequestParam String role){

        int total=userService.findAllUsers().size();
        List<User>roles=userService.findByRoles(role);
        List<User> foundedByName=userService.findByName(name);
        List<User>totalList=new ArrayList<>();
        totalList.addAll(roles);
        totalList.addAll(foundedByName);

        for(int i=0;i<totalList.size();i++){
            for(int j=i+1;j<totalList.size();j++)
                if(totalList.get(i).equals(totalList.get(j)))
                    totalList.remove(j);
        }

        PageModel pageModel=new PageModel();

        if(!totalList.isEmpty()){
            pageModel.setPageSize(pageSie);
            pageModel.setPageNumber(pageNumber);
            pageModel.setTotalPages((int) Math.ceil((double) totalList.size()/(double) pageSie));
            if(pageSie<=totalList.size())
                pageModel.setList(totalList.subList((pageNumber)*pageSie,(pageNumber+1)*pageSie));
            else
                pageModel.setList(totalList);
            return  pageModel;
        }

        pageModel.setPageSize(pageSie);
        pageModel.setPageNumber(pageNumber);
        pageModel.setTotalPages((int) Math.ceil((double) total/(double)pageSie));
        pageModel.setList(userService.findPaginated(pageNumber,pageSie));
        return  pageModel;
    }

    /*
    http://localhost:8585/api/Users/Profile
    POST
    */
    @RequestMapping(value = "/Profile", method = RequestMethod.POST)
    public  User ChangeUserProfile(@RequestBody User user){
        User user2=userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user.getName()!=null)
            user2.setName(user.getName());
        if(user.getLastname()!=null)
            user2.setLastname(user.getLastname());
        if(user.getPassword()!=null)
            user2.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if(user.getBirthday()!=null)
            user2.setBirthday(user.getBirthday());
        if(user.getCover()!=null)
            user2.setCover(user.getCover());
        if(user.getCreatedAt()!=null)
            user2.setCreatedAt(user.getCreatedAt());
        if(user.getEmail()!=null)
            user2.setEmail(user.getEmail());
        if(user.getUpdatedAt()!=null)
            user2.setUpdatedAt(user.getUpdatedAt());
        if(user.getRoles()!=null)
            user2.setRoles(user.getRoles());

        return  userService.saveUser(user2);

    }

    /*
    http://localhost:8585/api/Users/Profile/ChangePassword
    POST
    */
    @RequestMapping(value = "/Profile/ChangePassword", method = RequestMethod.POST)
    public  User ChangeUserPassword(@RequestParam String currentPassword,@RequestParam String newPassword){

        User user=userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user.getPassword().equals(currentPassword)){
            user.setPassword(newPassword);
            userService.saveUser(user);
        }
        return user;

    }

}
