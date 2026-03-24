package com.example.demo.dto;

import com.example.demo.model.adventure.Category;

import java.time.LocalDateTime;

public record AdventurerDetailsResponseDTO(
        Long id,
        OrganizationResponseDTO organization,
        String name,
        Category category,
        Integer level,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CompanionResponseDTO companion,
        Integer totalMissions,
        MissionResponseDTO lastMission
) {




}
