package com.example.springcourse.repository;

import com.example.springcourse.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Long insert(User user){
        entityManager.persist(user);
        return user.getId();
    }
}
