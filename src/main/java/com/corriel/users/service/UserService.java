package com.corriel.users.service;

import com.corriel.users.entity.SystemUser;
import com.corriel.users.repository.UserDao;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class UserService {

    private final UserDao userDao;

    @Inject
    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public SystemUser find(String username) {
        return userDao.find(username);
    }

    public SystemUser getCurrentUser() {
        String userName = getCurrentUserName();
        return find(userName);
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
