package com.example.pywo.service;

import com.example.pywo.model.User;

public interface UserService {
    User findUserByUsername(String username);
}
