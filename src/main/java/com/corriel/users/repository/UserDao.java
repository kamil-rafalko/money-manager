package com.corriel.users.repository;

import com.corriel.data.repository.GenericDao;
import com.corriel.users.entity.SystemUser;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends GenericDao<SystemUser, String> {

    public UserDao() {
        super(SystemUser.class);
    }
}
