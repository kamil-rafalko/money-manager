package com.corriel.users.repository;

import com.corriel.users.entity.User;

public interface UserDao {

    User findByUserName(String username);
}
