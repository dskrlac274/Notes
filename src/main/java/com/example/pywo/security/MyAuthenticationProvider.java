package com.example.pywo.security;


import com.example.pywo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    private final HttpServletRequest request;

    public MyAuthenticationProvider(UserRepository userRepository, MyUserDetailsService myUserDetailsService, HttpServletRequest request){
        this.myUserDetailsService = myUserDetailsService;
        this.userRepository = userRepository;
        this.request = request;
    }

    @Override
    @Transactional
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final UserDetails user = myUserDetailsService.loadUserByUsername(auth.getName());
        String encodedPassword=passwordEncoder.encode((CharSequence) user.getPassword());

        if(!passwordEncoder.matches((CharSequence) auth.getCredentials(), encodedPassword))
            System.out.println("Incorrect password");//throw new InvalidPassword("Incorrect password");
        System.out.println("lozinka je tocna");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, auth.getCredentials(), user.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        return usernamePasswordAuthenticationToken;
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
