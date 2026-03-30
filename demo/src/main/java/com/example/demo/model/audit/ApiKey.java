package com.example.demo.model.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "api_keys",schema = "audit",uniqueConstraints =
@UniqueConstraint(columnNames = {"organizacao_id","nome"}))
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id",nullable = false)
    private Organization organization;

    @Column(name = "nome",nullable = false)
    private String name;

    @Column(name = "key_hash",nullable = false)
    private String hashKey;

    @Column(name = "ativo",nullable = false)
    private Boolean active;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_used_at",nullable = false)
    private LocalDateTime lastUsedAt;

    @OneToMany(mappedBy = "apiKey")
    private List<AuditEntry> auditEntries;
}
