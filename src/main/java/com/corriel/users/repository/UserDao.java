package com.corriel.users.repository;

import com.corriel.data.repository.GenericDao;
import com.corriel.users.entity.SystemUser;

public interface UserDao extends GenericDao<SystemUser, String> {
}
