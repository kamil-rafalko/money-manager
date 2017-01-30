package com.corriel.users.service;

import com.corriel.users.entity.SystemUser;

public interface UserService {
    SystemUser find(String username);
    SystemUser getCurrentUser();
}
