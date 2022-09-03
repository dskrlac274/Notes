package com.example.pywo.repository;

import com.example.pywo.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRoleRepository extends CrudRepository<UserRole,Long> {
    @Query("select u from UserRole u where u.userRoleType = :name")
    Optional<UserRole> findByUserRoleType(@Param("name") String name);

}
