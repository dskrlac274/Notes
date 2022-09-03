package com.example.pywo.model;

import com.example.pywo.audit.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EntityListeners(Auditable.class)
@EqualsAndHashCode(exclude = "userRoles")
public class Privilege  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges",fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();

    public Privilege() {
    }

    public Privilege(String name) {
        this.name = name;
    }

}
