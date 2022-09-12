package com.example.pywo.service;

import com.example.pywo.exception.IdDoesNotExist;
import com.example.pywo.exception.UsernameAlreadyExist;
import com.example.pywo.jwt.JwtUtils;
import com.example.pywo.model.User;
import com.example.pywo.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).get();
    }

    @Override
    public User register(User user) {
        if(checkIfUsernameExist(user.getUsername())){
            throw new UsernameAlreadyExist("Username already exist");
        }
        return userRepository.save(user);
    }
    @Override
    public boolean checkIfUsernameExist(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    @Override
    public User updateUser(User oldUserDetails, User userToUpdate) {
        User user = userRepository.findById(oldUserDetails.getId()).map(
                usr->{
                    usr.mapFrom(userToUpdate);
                    return userRepository.save(usr);
                }).orElseThrow(() -> new IdDoesNotExist("Id does not exist!"));

        return user;
    }

    @SneakyThrows
    @Override
    public Byte[] saveImageFile(User loggedUser, MultipartFile file) {
        User user = userRepository.findById(loggedUser.getId()).orElseThrow(
                ()->new IdDoesNotExist("Id does not exist"));

        Byte[] byteObjects = new Byte[file.getBytes().length];

        int i = 0;

        for (byte b : file.getBytes())
            byteObjects[i++] = b;

        user.setImage(byteObjects);
        userRepository.save(user);
        return byteObjects;

    }

    @Override
    public Byte[] getImageFile(User loggedUser) {
        return loggedUser.getImage();
    }
}
