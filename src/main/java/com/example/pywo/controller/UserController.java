package com.example.pywo.controller;

import com.example.pywo.converter.UserConverter;
import com.example.pywo.dto.UserProfileDto;
import com.example.pywo.form.LoginForm;
import com.example.pywo.form.ProfileUpdateForm;
import com.example.pywo.form.RegisterForm;
import com.example.pywo.jwt.AuthTokenFilter;
import com.example.pywo.jwt.JwtUtils;
import com.example.pywo.model.User;
import com.example.pywo.security.AuthenticationResponse;
import com.example.pywo.security.MyUserDetailsService;
import com.example.pywo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping(value = "/profile",consumes = MediaType.ALL_VALUE)
    public ResponseEntity<UserProfileDto> profile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = userService.findUserByUsername(authentication.getName());
        /*UserDetails userDetails = null;
        try {
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        } */
        User user = userService.findUserByUsername(userDetails.getUsername());
        UserProfileDto userProfileDto = new UserProfileDto(user.getFirstName(),user.getLastName(),user.getUsername(),
            user.getEmail(),user.getPassword());
        return new ResponseEntity<UserProfileDto>(userProfileDto,HttpStatus.OK);
    }
    @PutMapping(value = "/profile",consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> updateProfile(@ModelAttribute ProfileUpdateForm profileUpdateForm) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User oldUserDetails = userService.findUserByUsername(auth.getName());


        return new ResponseEntity<>(userService.updateUser(oldUserDetails, userConverter.convertProfileUpdateFormToUser(profileUpdateForm)),HttpStatus.OK);
    }
    @PostMapping(value = "/image",consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Byte[]> handleImagePost(@ModelAttribute MultipartFile file){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());

        return new ResponseEntity<>(userService.saveImageFile(user,file),HttpStatus.OK);
    }
    @GetMapping(value = "/image",consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Byte[]> handleImageGet(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());

        return new ResponseEntity<>(userService.getImageFile(user),HttpStatus.OK);
    }

}
