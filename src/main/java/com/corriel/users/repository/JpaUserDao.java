package com.corriel.users.repository;

import com.corriel.users.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaUserDao implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public User findByUserName(String username) {
        return  entityManager.find(User.class, username);
    }
}
