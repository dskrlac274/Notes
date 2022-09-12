package com.example.pywo.converter;

import com.example.pywo.config.SetupDataConfig;
import com.example.pywo.form.ProfileUpdateForm;
import com.example.pywo.form.RegisterForm;
import com.example.pywo.jwt.AuthTokenFilter;
import com.example.pywo.jwt.JwtUtils;
import com.example.pywo.model.Privilege;
import com.example.pywo.model.User;
import com.example.pywo.model.UserRole;
import com.example.pywo.security.MyUserDetailsService;
import com.example.pywo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserConverter {

    @Autowired
    private SetupDataConfig setupDataConfig;

    @Autowired
    UserService userService;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private MyUserDetailsService userDetailsService;

    public User convertRegisterFormToUser(RegisterForm registerForm){
        Privilege readPrivilege = setupDataConfig.createPrivilegeOrReturnExisting("READ_PRIVILEGE");
        Set<Privilege> userPrivileges = new HashSet<>();
        userPrivileges.add(readPrivilege);
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(setupDataConfig.createRoleIfNotFound("ROLE_USER", userPrivileges));

        return new User(registerForm.getFirstName(), registerForm.getLastName(), registerForm.getEmail(),
                registerForm.getUsername(), registerForm.getPassword(),userRoles);
    }
    public User convertProfileUpdateFormToUser(ProfileUpdateForm profileUpdateForm){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());

        return new User(user.getFirstName(), user.getLastName(), profileUpdateForm.getEmail(), user.getUsername(), profileUpdateForm.getPassword(),
                user.getUserRoles());
    }
}
