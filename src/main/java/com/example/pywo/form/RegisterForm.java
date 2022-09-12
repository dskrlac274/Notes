package com.example.pywo.form;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class RegisterForm {
    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;
}
