package com.example.demo.model.audit;



import com.example.demo.model.adventure.Adventurer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios", schema = "audit",uniqueConstraints = @UniqueConstraint(
        columnNames = {"organizacao_id","email"}
) )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id",nullable = false)
    @JsonIgnore
    private Organization organization;

    @Column(name = "nome",nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "senha_hash",nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String status;

    @Column(name = "ultimo_login_em",nullable = false)
    private LocalDateTime lastLoginAt;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            schema = "audit",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;


    @OneToMany(mappedBy = "user")
    private List<AuditEntry> auditEntries;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Adventurer> adventurers;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
