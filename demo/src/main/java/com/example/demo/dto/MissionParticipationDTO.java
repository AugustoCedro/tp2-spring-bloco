package com.example.demo.dto;

import com.example.demo.model.adventure.MissionRole;

public record MissionParticipationDTO(
        Long id,
        AdventurerResponseDTO adventurer,
        MissionRole missionRole,
        Integer rewardInGold,
        Boolean mvp
) {
}
