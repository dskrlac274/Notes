package com.example.pywo.controller;


import com.example.pywo.form.LoginForm;
import com.example.pywo.jwt.JwtUtils;
import com.example.pywo.security.AuthenticationResponse;
import com.example.pywo.security.MyAuthenticationProvider;
import com.example.pywo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;
    @Autowired
    private JwtUtils jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> login(@ModelAttribute("userLoginFormData") LoginForm user) {

        System.out.println("User data je:" + user.getPassword() + user.getUsername());

        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(), user.getPassword()
                        )
                );
        System.out.println("USER JE:" + " " + userService.findUserByUsername(user.getUsername()).getPassword());
        String accessToken = jwtUtil.generateAccessToken(userService.findUserByUsername(user.getUsername()));
        System.out.println("dva");
        AuthenticationResponse response = new AuthenticationResponse(user.getUsername(),accessToken);

        return ResponseEntity.ok().body(response);

    }

}
