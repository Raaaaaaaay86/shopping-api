package com.example.shoppingapi.controller;

import com.example.shoppingapi.dto.DefaultResponse;
import com.example.shoppingapi.entity.User;
import com.example.shoppingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    DefaultResponse login(@RequestBody Map<String, User> request, HttpServletResponse response) throws IOException {
        var user = request.get("data");
        var username= user.getUsername();
        var password = user.getPassword();

        var JWTTokenOptional = userService.login(username, password);

        if (JWTTokenOptional.isEmpty()) {
            return new DefaultResponse(false, "failed");
        }
        var JWTToken = JWTTokenOptional.get();

        response.setHeader(HttpHeaders.AUTHORIZATION, JWTToken);

        return new DefaultResponse(true, "success");
    }


    @PostMapping("/register")
    DefaultResponse register(@RequestBody Map<String, User> request) {
        var registerUser = request.get("data");

        try {
            userService.register(registerUser);
        } catch (Exception e) {
            System.out.println("Register failed...");
        }

        return new DefaultResponse(true, "success");
    }
}
