package com.example.demo.dto;

public record AdventurerSearchResponseDTO(
        Long id,
        String name,
        Boolean active,
        OrganizationResponseDTO organization,
        CompanionResponseDTO companion
) {
}
