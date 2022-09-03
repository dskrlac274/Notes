package com.example.pywo.repository;

import com.example.pywo.model.Privilege;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PrivilegeRepository extends CrudRepository<Privilege,Long> {
    @Query("select p from Privilege p where p.name = :name")
    Optional<Privilege> findByName(@Param("name") String name);
}
