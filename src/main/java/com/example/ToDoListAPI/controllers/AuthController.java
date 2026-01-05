package com.example.ToDoListAPI.controllers;

import com.example.ToDoListAPI.model.User;
import com.example.ToDoListAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Map<String,String> register(@RequestBody User user){
        String token = userService.registerUser(user);
        return Collections.singletonMap("token",token);
    }

    @PostMapping("/login")
    public Map<String,String> login (@RequestBody Map<String, String> body){
        String token = userService.loginUser(body.get("email"), body.get("password"));
        return Collections.singletonMap("token",token);
    }
}
