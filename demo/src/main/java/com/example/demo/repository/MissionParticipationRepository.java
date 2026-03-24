package com.example.demo.repository;

import com.example.demo.model.adventure.Mission;
import com.example.demo.model.adventure.MissionParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissionParticipationRepository extends JpaRepository<MissionParticipation,Long> {
    Optional<MissionParticipation> findTopByAdventurerIdOrderByMissionCreatedAtDesc(Long adventurerId);
}
