package com.example.demo.dto;

import jakarta.validation.constraints.*;

public record AdventurerRequestDTO(
    @NotBlank
    String organization,
    @NotBlank
    String user,
    @NotBlank
    @Size(min = 3,max = 120)
    String name,
    @NotNull
    String category,
    @NotNull
    @Positive
    Integer level
) {
}
