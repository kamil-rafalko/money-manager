package com.corriel.users.repository;

import com.corriel.data.repository.JpaGenericDao;
import com.corriel.users.entity.SystemUser;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUserDao extends JpaGenericDao<SystemUser, String> implements UserDao {

    public JpaUserDao() {
        super(SystemUser.class);
    }
}
