package com.example.demo.dto;


import java.util.List;

public record ErrorFieldDTO(
        String field,
        List<String> messages
) {
}
