package com.example.demo.dto;

import com.example.demo.model.adventure.Specie;

public record CompanionResponseDTO(
        Long id,
        String name,
        Specie specie,
        Integer loyalty
) {
}
