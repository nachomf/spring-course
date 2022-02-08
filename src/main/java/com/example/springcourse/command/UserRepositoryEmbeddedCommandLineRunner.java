package com.example.springcourse.command;

import com.example.springcourse.model.User;
import com.example.springcourse.repository.UserRepositoryEmbedded;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryEmbeddedCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryEmbeddedCommandLineRunner.class);

    private final UserRepositoryEmbedded userRepository;

    public UserRepositoryEmbeddedCommandLineRunner(UserRepositoryEmbedded userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Jill", new Date(), RandomStringUtils.randomAlphanumeric(15));
        userRepository.save(user);
        log.info("User embedded is created " + user.getId());

        Optional<User> userWithId = userRepository.findById(1L);
        log.info("User retrieve " + userWithId);

        List<User> users = userRepository.findAll();
        log.info("Users created: " + users);
    }
}
