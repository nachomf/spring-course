package com.example.springcourse.users.service;

import com.example.springcourse.users.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "Pepe", new Date()));
        users.add(new User(2, "Maria", new Date()));
        users.add(new User(3, "Juan", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(users.size() + 1);
        }
        users.add(user);
        return user;
    }

    public User findOne(Integer id){
        return users
                .stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst()
                .orElse(null);
    }
}
