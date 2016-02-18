package com.corriel.users.service;

import com.corriel.users.dao.UserDao;
import com.corriel.users.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.corriel.users.model.User user = userDao.findByUserName(username);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRoles());

        return buildUserForAuthentication(user, authorities);

    }

    private User buildUserForAuthentication(com.corriel.users.model.User user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        for(UserRole userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }

        ArrayList<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);

        return result;
    }


}
