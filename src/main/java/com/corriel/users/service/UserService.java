package com.corriel.users.service;

import com.corriel.users.entity.SystemUser;

public interface UserService {
    SystemUser getUserWithBudgets(String username);
    String getCurrentUserName();
}
