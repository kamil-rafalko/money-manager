package com.corriel.users.service;

import com.corriel.users.entity.SystemUser;

import java.util.Optional;

public interface UserService {
    Optional<SystemUser> getUserWithBudgets(String username);
    Optional<SystemUser> getCurrentUserWithBudgets();
}
