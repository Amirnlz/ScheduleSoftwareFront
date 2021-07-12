package com.finalproject.schedule.Modules.User.service;

import com.finalproject.schedule.Modules.User.model.User;
import com.finalproject.schedule.Modules.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional /* add one user with image */
    public User registerUser(User user) throws IOException, InvocationTargetException, IllegalAccessException {
        if (!user.getFile().isEmpty()) {
            String path = ResourceUtils.getFile("classpath:static/assets/usercover/").getAbsolutePath();
            byte[] bytes = user.getFile().getBytes();
            String name = UUID.randomUUID() + "." + Objects.requireNonNull(user.getFile().getContentType()).split("/")[1];
            Files.write(Paths.get(path + File.separator + name), bytes);
            user.setCover(name);
        }

        if (!user.getPassword().isEmpty()){
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    @Transactional /* add one user */
    public User saveUser(User user){
        if (!user.getPassword().isEmpty()){
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Transactional /* add user as a group */
    public List<User> saveUserList(List<User> user){
        for(int i=0; i<user.size(); i++){
            if (!user.get(i).getPassword().isEmpty()){
                user.get(i).setPassword(new BCryptPasswordEncoder().encode(user.get(i).getPassword()));
            }
        }
        return userRepository.saveAll(user);
    }

    /* find all users and return in a list */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /* find all users and return in a page */
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /* find user by id */
    public User findById(int id){
        return userRepository.findById(id);
    }

    /* use when try to find user by code in principal (spring.security) */
    public User findByCode(String code){
        return userRepository.findByCode(code);
    }

    /* delete user by id */
    public void deleteById(int id){
        userRepository.deleteById(id);
    }

    /* give role string and find its count and use it in admin_main html page's chart*/
    public  List<User>findByRoles(String role){
        List<User> temp = new ArrayList<>();
        for (User user : userRepository.findAll()){
            if(user.getRoles().get(0).toString().contentEquals(role)){
                temp.add(user);
            }
        }
        return temp;
    }

    /* find user list by name */
    public  List<User>findByName(String name){
        List<User>temp=new ArrayList<>();
        for (User user:userRepository.findAll()){
            if(user.getName().equals(name)){
                temp.add(user);
            }
        }
        return temp;
    }

    /* Page */
    public List<User>findPaginated(int pageNumber,int pageSize){
        Pageable paging= PageRequest.of(pageNumber,pageSize);
        Page<User>page=userRepository.findAll(paging);
        return page.toList();
    }

}
