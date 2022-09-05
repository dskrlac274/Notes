package com.example.pywo.security;


import com.example.pywo.exception.InvalidCredentials;
import com.example.pywo.model.Privilege;
import com.example.pywo.model.User;
import com.example.pywo.model.UserRole;
import com.example.pywo.repository.UserRepository;
import com.example.pywo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;


    private UserRoleRepository userRoleRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
      User user = userRepository.findUserByUsername(s).orElseThrow(()-> new InvalidCredentials("Username not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), true, true, true,
                true, getAuthorities(user.getUserRoles()));
    }
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<UserRole> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }
    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
    private List<String> getPrivileges(Collection<UserRole> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> privilegeList = new ArrayList<>();

        for (UserRole role : roles) {
            privileges.add(role.getUserRoleType());
            privilegeList.addAll(role.getPrivileges());
        }
        for (Privilege privilege : privilegeList) {
            privileges.add(privilege.getName());
        }
        return privileges;
    }

}
