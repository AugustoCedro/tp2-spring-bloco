package com.example.demo.repository;

import com.example.demo.model.adventure.DangerLevel;
import com.example.demo.model.adventure.Mission;
import com.example.demo.model.adventure.MissionStatus;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission,Long> {
    Optional<Mission> findByTitleIgnoreCase(String title);

    @Query("""
    SELECT m FROM Mission m
    WHERE (:status IS NULL OR m.missionStatus = :status)
      AND (:dangerLevel IS NULL OR m.dangerLevel = :dangerLevel)
      AND (CAST(:startDate AS date) IS NULL OR m.createdAt >= :startDate)
      AND (CAST(:endDate AS date) IS NULL OR m.createdAt <= :endDate)
""")
    Page<Mission> findWithFilters(
            @Param("status") MissionStatus status,
            @Param("dangerLevel") DangerLevel dangerLevel,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );



}
