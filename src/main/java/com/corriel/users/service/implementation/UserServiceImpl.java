package com.corriel.users.service.implementation;

import com.corriel.users.entity.SystemUser;
import com.corriel.users.repository.UserDao;
import com.corriel.users.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    public Optional<SystemUser> getUserWithBudgets(String username) {
        SystemUser systemUser = userDao.find(username);
        if (systemUser == null) {
            return Optional.empty();
        } else {
            Hibernate.initialize(systemUser.getBudgets());
            return Optional.of(systemUser);
        }
    }

    @Override
    public Optional<SystemUser> getCurrentUserWithBudgets() {
        String userName = getCurrentUserName();
        return getUserWithBudgets(userName);
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
