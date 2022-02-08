package com.example.springcourse.command;

import com.example.springcourse.model.User;
import com.example.springcourse.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryCommandLineRunner.class);

    private final UserRepository userRepository;

    public UserRepositoryCommandLineRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("pepe", new Date(), "abcd");
        long userId = userRepository.insert(user);
        log.info("New user is created "+ userId);
    }
}
