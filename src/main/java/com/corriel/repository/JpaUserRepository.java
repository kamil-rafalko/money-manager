package com.corriel.repository;

import com.corriel.application.core.entity.SystemUser;
import com.corriel.application.core.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaUserRepository extends JpaGenericRepository<SystemUser, String> implements UserRepository {
    JpaUserRepository() {
        super(SystemUser.class);
    }
}
