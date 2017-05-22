package com.corriel.application.core.users;

import com.corriel.application.core.entity.SystemUser;
import com.corriel.application.core.entity.UserRole;
import com.corriel.application.core.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Inject
    public AppUserDetailsService(final UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SystemUser user = repository.find(username);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRoles());

        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(SystemUser user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

        Set<GrantedAuthority> authorities = userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole()))
                .collect(Collectors.toSet());

        return new ArrayList<>(authorities);
    }
}
