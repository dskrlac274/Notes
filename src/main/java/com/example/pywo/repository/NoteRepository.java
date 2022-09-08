package com.example.pywo.repository;

import com.example.pywo.model.Note;
import com.example.pywo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query("select n from Note n where n.id = :id")
    Optional<Note> findById(@Param("id") long id);

    @Query("select n from Note n where n.user = ?1")
    List<Note> findByUser(User user);

    @Query("select n from Note n where n.user = :user and n.id = :id")
    Optional<Note> findByUserAndId(@Param("user") User user, @Param("id") long id);
}
