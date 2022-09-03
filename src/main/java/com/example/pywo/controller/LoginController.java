package com.example.pywo.controller;


import com.example.pywo.model.User;
import com.example.pywo.security.AuthenticationResponse;
import com.example.pywo.security.JwtTokenUtil;
import com.example.pywo.security.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;
    @Autowired
    private JwtTokenUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(), user.getPassword()
                        )
                );

        String accessToken = jwtUtil.generateAccessToken(user);

        AuthenticationResponse response = new AuthenticationResponse(user.getUsername(),accessToken);

        return ResponseEntity.ok().body(response);
    }


}
