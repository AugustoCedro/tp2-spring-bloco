package com.example.demo.model.adventure;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "participacoes_em_missao",schema = "aventura",uniqueConstraints = @UniqueConstraint(columnNames = {"missao_id","aventureiro_id"}))
@Entity
@Getter
@Setter
@NoArgsConstructor
public class MissionParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "missao_id",nullable = false)
    private Mission mission;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "aventureiro_id",nullable = false)
    private Adventurer adventurer;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_na_missao",nullable = false)
    private MissionRole missionRole;

    @Column(name = "recompensa_em_ouro")
    private Integer rewardInGold;

    @Column(nullable = false)
    private Boolean mvp;

    @Column(name = "register_date",nullable = false)
    private LocalDateTime registerDate;

    public MissionParticipation(Mission mission, Adventurer adventurer, MissionRole missionRole, Integer rewardInGold) {
        this.mission = mission;
        this.adventurer = adventurer;
        this.missionRole = missionRole;
        this.rewardInGold = rewardInGold;
        this.mvp = false;
        this.registerDate = LocalDateTime.now();
    }
}
