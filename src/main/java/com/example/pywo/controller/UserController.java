package com.example.pywo.controller;

import com.example.pywo.dto.UserProfileDto;
import com.example.pywo.form.LoginForm;
import com.example.pywo.model.User;
import com.example.pywo.security.AuthenticationResponse;
import com.example.pywo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping(value = "/profile")
    public ResponseEntity<UserProfileDto> login() {

        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        User user = userService.findUserByUsername(userDetails.getUsername());
        UserProfileDto userProfileDto = new UserProfileDto(user.getFirstName(),user.getLastName(),user.getUsername(),
            user.getEmail(),user.getPassword());
        return new ResponseEntity<UserProfileDto>(userProfileDto,HttpStatus.OK);

    }

}
