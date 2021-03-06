package com.example.springcourse.service;

import com.example.springcourse.exception.UserWithPostException;
import com.example.springcourse.model.User;
import com.example.springcourse.exception.NotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private static final List<User> users = new ArrayList<>();

    private final PostService postService;

    static {
        users.add(new User(1L, "Pepe", new Date(),""));
        users.add(new User(2L, "Maria", new Date(),"asd"));
        users.add(new User(3L, "Juan", new Date(),"1234"));
    }

    public UserService(PostService postService) {
        this.postService = postService;
    }

    public List<User> findAll() {
        return users;
    }

    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId((long) users.size() + 1L);
        }

        if (user.getPassword() == null) {
            user.setPassword(RandomStringUtils.randomAlphanumeric(15));
        }
        users.add(user);
        return user;
    }

    public void deleteUser(Long userId) {
        User user = this.findOne(userId);
        if (user == null) {
            throw new NotFoundException("User " + user.toString() + " not found");
        }

        if (!postService.findAll(userId).isEmpty()){
            throw new UserWithPostException();
        }

        users.remove(user);
    }

    public User findOne(Long id) {
        return users
                .stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User " + id.toString() + " not found"));
    }
}
