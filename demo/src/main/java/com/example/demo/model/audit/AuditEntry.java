package com.example.demo.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "audit_entries",schema = "audit")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id",nullable = false)
    private Organization organization;

    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_user_id",nullable = true)
    @JsonIgnore
    private User user;

    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_api_key_id",nullable = true)
    private ApiKey apiKey;

}
