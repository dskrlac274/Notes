package com.example.pywo.model;

import com.example.pywo.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"user"})
@ToString(exclude = {"user"})
public class Note extends Auditable<String> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String description;

    private String title;

 /*   private Integer numberOfCharacters;

    private Integer numberOfWords;

    private Integer numberOfMistakes;*/
    @ManyToOne
    @JsonBackReference(value = "note-user")
    private User user;
    public Note(String description, String title, User user){
        this.description = description;
        this.title = title;
        this.user = user;
    }
    public void mapFrom(Note source) {
        this.setTitle(source.getTitle());
        this.setDescription(source.getDescription());
    }

}
