package com.example.demo.model.adventure;

import com.example.demo.model.audit.Organization;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "vw_painel_tatico_missao",schema = "operacoes")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class MissionTacticalPanel {

    @Id
    @Column(name = "missao_id")
    private Long missionId;

    @Column(name = "titulo", nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MissionStatus status;

    @Column(name = "nivel_perigo", nullable = false)
    @Enumerated(EnumType.STRING)
    private DangerLevel dangerLevel;

    @Column(name = "organizacao_id", nullable = false)
    private Long organizationId;

    @Column(name = "total_participantes", nullable = false)
    private Integer totalParticipants;

    @Column(name = "nivel_medio_equipe", nullable = false)
    private Double teamAverageLevel;

    @Column(name = "total_recompensa", nullable = false)
    private Integer totalReward;

    @Column(name = "total_mvps", nullable = false)
    private Integer totalMvps;

    @Column(name = "participantes_com_companheiro", nullable = false)
    private Integer totalCompanions;

    @Column(name = "ultima_atualizacao", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "indice_prontidao", nullable = false)
    private Double readinessIndex;
}
