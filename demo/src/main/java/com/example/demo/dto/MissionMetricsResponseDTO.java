package com.example.demo.dto;

import com.example.demo.model.adventure.DangerLevel;
import com.example.demo.model.adventure.MissionStatus;

public record MissionMetricsResponseDTO(
        String title,
        MissionStatus missionStatus,
        DangerLevel dangerLevel,
        Integer totalParticipants,
        Integer totalRewardInGold
) {
}
