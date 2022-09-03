package com.example.pywo.model;


import com.example.pywo.audit.Auditable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
@EntityListeners(Auditable.class)
public class User /*extends Auditable<String> */{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    private String firstName;

    @Size(max = 200)
    private String lastName;

    private String email;

    private String password;
    private String username;

    //add image
    //add image to constructor
    @OneToMany(mappedBy = "user")
    Set<Note> note = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<UserRole> userRoles = new HashSet<>();

    public User(String firstName, String lastName, String email, String password, String username,Set<UserRole> userRoles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.userRoles = userRoles;
    }
}
