package com.example.demo.dto;


import com.example.demo.model.adventure.Category;

public record AdventurerResponseDTO(
        Long id,
        String name,
        Category category,
        Integer level,
        Boolean active
) {
}
