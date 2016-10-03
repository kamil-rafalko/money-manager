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
    public SystemUser getUserWithBudgets(String username) {
        SystemUser systemUser = userDao.find(username);
        systemUser.getBudgets();
        return systemUser;
    }

    @Override
    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}