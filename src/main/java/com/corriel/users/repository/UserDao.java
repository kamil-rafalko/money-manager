package com.corriel.users.repository;

import com.corriel.users.model.User;

public interface UserDao {

    User findByUserName(String username);
}
