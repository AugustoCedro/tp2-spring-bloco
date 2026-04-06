package com.example.demo.dto;

import com.example.demo.model.adventure.DangerLevel;
import com.example.demo.model.adventure.MissionStatus;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record MissionResponseDTO(
        Long id,
        String organization,
        String title,
        DangerLevel dangerLevel,
        MissionStatus missionStatus,
        LocalDateTime createdAt,
        LocalDateTime startedAt,
        LocalDateTime finishedAt
) {
}
