package com.example.demo.repository;

import com.example.demo.model.adventure.Adventurer;
import com.example.demo.model.adventure.Category;
import com.example.demo.model.adventure.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;


public interface AdventurerRepository extends JpaRepository<Adventurer,Long> {

    @Query("""
        SELECT a FROM Adventurer a
        WHERE (:active IS NULL OR a.active = :active)
        AND (:category IS NULL OR a.category = :category)
        AND (:minLevel IS NULL OR a.level >= :minLevel)
""")
    Page<Adventurer> findWithFilters(
            @Param("active") Boolean active,
            @Param("category") Category category,
            @Param("minLevel")Integer minLevel,
            Pageable pageable
            );


    Page<Adventurer> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<Adventurer> findByIdAndOrganizationId(Long adventurerId, Long id);

    @Query("""
    SELECT a FROM Adventurer a
    LEFT JOIN a.participations p
    LEFT JOIN p.mission m
    WHERE (:status IS NULL OR m.missionStatus = :status)
      AND (:startDate IS NULL OR m.createdAt >= :startDate)
      AND (:endDate IS NULL OR m.createdAt <= :endDate)
    GROUP BY a
    ORDER BY 
        COUNT(p) DESC,
        COALESCE(SUM(p.rewardInGold), 0) DESC,
        COALESCE(SUM(CASE WHEN p.mvp = true THEN 1 ELSE 0 END), 0) DESC
""")
    Page<Adventurer> findRanking(
            @Param("status") MissionStatus status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );
}
