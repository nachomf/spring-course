package com.example.springcourse.controller;

import com.example.springcourse.exception.NotFoundException;
import com.example.springcourse.model.User;
import com.example.springcourse.repository.UserRepositoryEmbedded;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJPAController {

    private final UserRepositoryEmbedded service;

    public UserJPAController(UserRepositoryEmbedded service) {
        this.service = service;
    }

    @GetMapping("/jpa/users")
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/jpa/user-names")
    public MappingJacksonValue getAllUserNames() {
        List<User> users = service.findAll();

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(
                new SimpleFilterProvider()
                        .setFailOnUnknownId(false)
                        .addFilter(
                                "UsersFilter",
                                SimpleBeanPropertyFilter.filterOutAllExcept("name")
                        )
        );

        return mapping;
    }

    @GetMapping("/jpa/users/{userId}")
    public EntityModel<User> getUser(@PathVariable Long userId) {
        Optional<User> user = service.findById(userId);

        if(user.isEmpty()){
            throw new NotFoundException("User " + userId +  "not found");
        }

        return EntityModel
                .of(user.get())
                .add(linkTo(methodOn(getClass()).getAllUsers()).withRel("all-users"));
    }

    @PostMapping("/jpa/users")
    public EntityModel createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

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

    @DeleteMapping("/jpa/users/{userId}")
    public ResponseEntity<URI> deleteUser(@PathVariable Long userId) {
        service.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
