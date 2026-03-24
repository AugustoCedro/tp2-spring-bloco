package com.example.demo.model.adventure;

import com.example.demo.model.audit.Organization;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "missoes",schema = "aventura")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao",nullable = false)
    private Organization organization;

    @Column(name = "title",nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_de_perigo",nullable = false)
    private DangerLevel dangerLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private MissionStatus missionStatus;

    @Column(name = "created_at",nullable = false)
    private LocalDate createdAt;

    @Column(name = "started_at")
    private LocalDate startedAt;

    @Column(name = "finished_at")
    private LocalDate finishedAt;

    @OneToMany(
            mappedBy = "mission",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<MissionParticipation> participations;


    public Mission(Organization organization, String title, DangerLevel dangerLevel, MissionStatus missionStatus, LocalDate createdAt) {
        this.organization = organization;
        this.title = title;
        this.dangerLevel = dangerLevel;
        this.createdAt = createdAt;
        this.missionStatus = missionStatus;

        missionStatus.apply(this);
    }
}
