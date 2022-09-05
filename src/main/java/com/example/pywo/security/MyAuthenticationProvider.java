package com.example.pywo.security;


import com.example.pywo.exception.InvalidCredentials;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
        System.out.println("User u eutentifikaciji je" + auth.getName());

        final UserDetails user = myUserDetailsService.loadUserByUsername(auth.getName());
        System.out.println("Nakon detailts user je" + " " + user.getUsername());
        String encodedPassword=passwordEncoder.encode((CharSequence) user.getPassword());
        if(!passwordEncoder.matches((CharSequence) auth.getCredentials(), encodedPassword))
                throw new InvalidCredentials("Invalid password");

        System.out.println("lozinka je tocna" + user.getAuthorities());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        System.out.println("jedan");
        return usernamePasswordAuthenticationToken;
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
