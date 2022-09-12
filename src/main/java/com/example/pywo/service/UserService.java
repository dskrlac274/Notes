package com.example.pywo.service;

import com.example.pywo.model.User;

public interface UserService {
    User findUserByUsername(String username);

    User register(User user);

    boolean checkIfUsernameExist(String username);

    User updateUser(User oldUserDetails, User userToUpdate);
}
