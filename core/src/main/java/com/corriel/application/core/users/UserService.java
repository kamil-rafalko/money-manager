package com.corriel.application.core.users;

import com.corriel.application.core.annotations.ApplicationService;
import com.corriel.application.core.entity.SystemUser;
import com.corriel.application.core.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.inject.Inject;

@ApplicationService
public class UserService {

    private final UserRepository repository;

    @Inject
    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    public SystemUser getCurrentUser() {
        String userName = getCurrentUserName();
        return repository.find(userName);
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
