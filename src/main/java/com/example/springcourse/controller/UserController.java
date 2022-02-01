package com.example.springcourse.controller;

import com.example.springcourse.model.User;
import com.example.springcourse.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public User getUser(@PathVariable Integer userId){
        return service.findOne(userId);
    }

    @PostMapping("/users")
    public ResponseEntity<URI> createUser(@RequestBody User user){
        User savedUser = service.saveUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
