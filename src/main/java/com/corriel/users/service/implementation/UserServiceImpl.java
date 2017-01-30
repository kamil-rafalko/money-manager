package com.corriel.users.service.implementation;

import com.corriel.users.entity.SystemUser;
import com.corriel.users.repository.UserDao;
import com.corriel.users.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    public SystemUser find(String username) {
        return userDao.find(username);
    }

    @Override
    public SystemUser getCurrentUser() {
        String userName = getCurrentUserName();
        return find(userName);
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
