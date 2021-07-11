package com.finalproject.schedule.Modules.Others.Login;

import com.finalproject.schedule.Jwt.JwtAuth;
import com.finalproject.schedule.Jwt.JwtUtils;
import com.finalproject.schedule.Modules.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginRestController {

    private final UserService userService;
    private final AuthenticationManager manager;
    private final JwtUtils jwtUtils;

    @Autowired
    public LoginRestController(UserService userService, AuthenticationManager manager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.manager = manager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/api/jwt/login")
    public ResponseEntity<?> jwtLogin(@RequestBody JwtAuth jwtAuth, HttpServletResponse response) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(jwtAuth.getUsername(), jwtAuth.getPassword()));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        response.addHeader("Authorization", jwtUtils.generateToken(jwtAuth.getUsername()));

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
