package com.example.demo.dto;


import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
        String message,
        Integer status,
        LocalDateTime timestamp,
        List<ErrorFieldDTO> details
) {

}