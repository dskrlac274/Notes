package com.example.pywo.controller;

import com.example.pywo.converter.UserConverter;
import com.example.pywo.dto.UserProfileDto;
import com.example.pywo.form.LoginForm;
import com.example.pywo.form.RegisterForm;
import com.example.pywo.model.User;
import com.example.pywo.model.UserRole;
import com.example.pywo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class RegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@ModelAttribute RegisterForm registerForm) {

        return new ResponseEntity<>(userService.register(userConverter.convertRegisterFormToUser(registerForm)),HttpStatus.CREATED);
    }
}
