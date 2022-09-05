package com.example.pywo.model;

import com.example.pywo.audit.Auditable;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Note extends Auditable<String> {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    private String description;

    private String title;

    private Integer numberOfCharacters;

    private Integer numberOfWords;

    private Integer numberOfMistakes;
    @ManyToOne
    private User user;

}
