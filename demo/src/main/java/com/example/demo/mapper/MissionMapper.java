package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.model.adventure.Mission;
import com.example.demo.model.adventure.MissionParticipation;
import org.springframework.stereotype.Component;

@Component
public class MissionMapper {

    public MissionParticipationDTO toParticipationDTO(MissionParticipation mission, AdventurerResponseDTO adventurerResponseDTO) {
        return new MissionParticipationDTO(
                mission.getId(),
                adventurerResponseDTO,
                mission.getMissionRole(),
                mission.getRewardInGold(),
                mission.getMvp()
        );
    }

    public MissionDetailsResponseDTO toMissionDetailsResponseDTO(Mission mission, AdventurerMapper adventurerMapper) {
        return new MissionDetailsResponseDTO(
                mission.getId(),
                mission.getOrganization().getName(),
                mission.getTitle(),
                mission.getDangerLevel(),
                mission.getMissionStatus(),
                mission.getParticipations()
                        .stream()
                        .map(p -> toParticipationDTO(p,adventurerMapper.toAdventurerResponseDTO(p.getAdventurer())))
                        .toList()
        );
    }

    public MissionResponseDTO toMissionResponseDTO(Mission mission){
        return new MissionResponseDTO(
                mission.getId(),
                mission.getOrganization().getName(),
                mission.getTitle(),
                mission.getDangerLevel(),
                mission.getMissionStatus(),
                mission.getCreatedAt(),
                mission.getStartedAt(),
                mission.getFinishedAt()
        );
    }

    public MissionMetricsResponseDTO toMissionMetricsResponseDTO(Mission mission){
        return new MissionMetricsResponseDTO(
                mission.getTitle(),
                mission.getMissionStatus(),
                mission.getDangerLevel(),
                mission.getParticipations().size(),
                mission.getParticipations().stream().map(MissionParticipation::getRewardInGold).reduce(0,Integer::sum)
        );
    }

}