package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.model.adventure.Adventurer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AdventurerMapper {

    private CompanionMapper companionMapper;
    private OrganizationMapper organizationMapper;

    public AdventurerResponseDTO toAdventurerResponseDTO(Adventurer adventurer){
       return new AdventurerResponseDTO(
               adventurer.getId(),
               adventurer.getName(),
               adventurer.getCategory(),
               adventurer.getLevel(),
               adventurer.getActive()

       );
    }

    public AdventurerSearchResponseDTO toAdventurerSearchResponseDTO(Adventurer adventurer){
        return new AdventurerSearchResponseDTO(
                adventurer.getId(),
                adventurer.getName(),
                adventurer.getActive(),
                organizationMapper.toOrganizationResponseDTO(adventurer.getOrganization()),
                adventurer.getCompanion() != null
                        ? companionMapper.toCompanionResponseDTO(adventurer.getCompanion())
                        : null
        );
    }

    public AdventurerDetailsResponseDTO toAdventurerDetailsResponseDTO(Adventurer adventurer, MissionResponseDTO mission){
        return new AdventurerDetailsResponseDTO(
                adventurer.getId(),
                organizationMapper.toOrganizationResponseDTO(adventurer.getOrganization()),
                adventurer.getName(),
                adventurer.getCategory(),
                adventurer.getLevel(),
                adventurer.getActive(),
                adventurer.getCreatedAt(),
                adventurer.getUpdatedAt(),
                adventurer.getCompanion() != null
                        ? companionMapper.toCompanionResponseDTO(adventurer.getCompanion())
                        : null,
                adventurer.getParticipations().size(),
                mission
        );
    }







}
