package com.example.pywo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
