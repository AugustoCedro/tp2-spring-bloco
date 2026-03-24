package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record MissionRequestDTO(
        @NotBlank
        String organization,
        @NotBlank
        String title
) {
}
