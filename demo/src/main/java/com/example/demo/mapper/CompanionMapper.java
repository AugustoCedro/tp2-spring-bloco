package com.example.demo.mapper;

import com.example.demo.dto.CompanionResponseDTO;
import com.example.demo.model.adventure.Companion;
import org.springframework.stereotype.Component;

@Component
public class CompanionMapper {

    public CompanionResponseDTO toCompanionResponseDTO(Companion companion){
        return new CompanionResponseDTO(
                companion.getId(),
                companion.getName(),
                companion.getSpecie(),
                companion.getLoyalty()
        );
    }


}
