package com.example.pywo.service;

import com.example.pywo.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User findUserByUsername(String username);

    User register(User user);

    boolean checkIfUsernameExist(String username);

    User updateUser(User oldUserDetails, User userToUpdate);

    Byte[] saveImageFile(User loggedUser, MultipartFile file);

    Byte[] getImageFile(User loggedUser);
}
