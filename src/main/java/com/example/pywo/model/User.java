package com.example.pywo.model;


import com.example.pywo.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"userRoles", "note"})
@Table(name = "users")
public class User /*extends Auditable<String> */{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

    @Lob
    private Byte[] image;

    //add image
    //add image to constructor
    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "note-user")
    Set<Note> note = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @JsonBackReference(value = "user-userRoles")
    private Set<UserRole> userRoles = new HashSet<>();

    public User(String firstName, String lastName, String email,String username, String password,Set<UserRole> userRoles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userRoles = userRoles;
    }
    public void mapFrom(User source) {
        this.setEmail(source.getEmail());
        this.setFirstName(source.getFirstName());
        this.setLastName(source.getLastName());
        this.setPassword(source.getPassword());
        this.setUsername(source.getUsername());

    }
}
