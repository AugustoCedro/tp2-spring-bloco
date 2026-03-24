package com.example.demo.dto;

import com.example.demo.model.adventure.DangerLevel;
import com.example.demo.model.adventure.MissionStatus;

import java.util.List;

public record MissionDetailsResponseDTO(
    Long id,
    String organization,
    String title,
    DangerLevel dangerLevel,
    MissionStatus missionStatus,
    List<MissionParticipationDTO> participations
) {
}
