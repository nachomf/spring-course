package com.example.springcourse.repository;

import com.example.springcourse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryEmbedded extends JpaRepository<User, Long> {
}
