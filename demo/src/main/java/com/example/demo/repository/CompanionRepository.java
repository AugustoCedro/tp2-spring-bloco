package com.example.demo.repository;

import com.example.demo.model.adventure.Companion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanionRepository extends JpaRepository<Companion,Long> {
}
