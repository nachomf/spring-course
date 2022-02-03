package com.example.springcourse.controller;

import com.example.springcourse.model.User;
import com.example.springcourse.service.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{userId}")
    public EntityModel<User> getUser(@PathVariable Integer userId) {
        User user = service.findOne(userId);

        return EntityModel
                .of(user)
                .add(linkTo(methodOn(getClass()).getAllUsers()).withRel("all-users"));
    }

    @PostMapping("/users")
    public EntityModel createUser(@Valid @RequestBody User user) {
        User savedUser = service.saveUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return EntityModel
                .of(ResponseEntity.created(location).build())
                .add(linkTo(methodOn(getClass()).getAllUsers()).withRel("all-users"))
                .add(linkTo(methodOn(getClass()).getUser(savedUser.getId())).withRel("one-user"))
                .add(linkTo(methodOn(getClass()).deleteUser(savedUser.getId())).withRel("delete-user"));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<URI> deleteUser(@PathVariable Integer userId) {
        service.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
