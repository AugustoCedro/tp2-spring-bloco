package com.example.demo.model.audit;

import com.example.demo.model.adventure.Adventurer;
import com.example.demo.model.adventure.Mission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "organizacoes",schema = "audit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome",unique = true,nullable = false)
    private String name;

    @Column(name = "ativo",nullable = false)
    private Boolean active;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "organization")
    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "organization")
    @JsonIgnore
    private List<ApiKey> apiKeys;

    @OneToMany(mappedBy = "organization")
    @JsonIgnore
    private List<AuditEntry> auditEntries;

    @OneToMany(mappedBy = "organization")
    @JsonIgnore
    private List<Adventurer> adventurers;

    @OneToMany(mappedBy = "organization")
    @JsonIgnore
    private List<Mission> missions;
}
