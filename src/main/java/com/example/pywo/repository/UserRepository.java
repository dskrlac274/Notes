package com.example.pywo.repository;

import com.example.pywo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    @Query("select u from User u where u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);
}
