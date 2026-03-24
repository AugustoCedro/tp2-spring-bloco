package com.example.demo.model.adventure;

import com.example.demo.model.audit.Organization;
import com.example.demo.model.audit.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


@Table(name = "aventureiros",schema = "aventura")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Adventurer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao",nullable = false)
    private Organization organization;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario",nullable = false)
    private User user;

    @Column(name = "nome",nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "classe",nullable = false)
    private Category category;

    @Column(name = "nivel",nullable = false)
    private Integer level;

    @Column(name = "ativo",nullable = false)
    private Boolean active;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(
            mappedBy = "adventurer",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private Companion companion;

    @OneToMany(
            mappedBy = "adventurer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<MissionParticipation> participations;

    public Adventurer(Integer level, Category category, String name, User user, Organization organization) {
        this.level = level;
        this.category = category;
        this.name = name;
        this.user = user;
        this.organization = organization;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Adventurer{" +
                "id=" + id +
                ", organization=" + organization +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", companion=" + companion +
                ", active=" + active +
                ", level=" + level +
                '}';
    }
}
