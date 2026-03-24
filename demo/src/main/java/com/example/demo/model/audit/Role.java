package com.example.demo.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "roles",schema = "audit", uniqueConstraints = @UniqueConstraint(columnNames = {"organizacao_id","nome"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organizacao_id",nullable = false)
    private Long organizationId;

    @Column(name = "nome",nullable = false)
    private String name;

    @Column(name = "descricao",nullable = false)
    private String description;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;


    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;

    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            schema = "audit",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    @JsonIgnore
    private Set<Permission> permissions;

}
