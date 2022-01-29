package com.example.springcourse.users.controller;

import com.example.springcourse.users.model.User;
import com.example.springcourse.users.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{userId}")
    public User getAllUsers(@PathVariable Integer userId){
        return service.findOne(userId);
    }
}
