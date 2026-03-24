package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.adventure.Mission;
import com.example.demo.service.MissionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/missoes")
@AllArgsConstructor
@Validated
public class MissionController {

    private MissionService service;

    @PostMapping
    public ResponseEntity<Mission> registerMission(@RequestBody @Valid MissionRequestDTO dto){
        Mission mission = service.register(dto);
        return ResponseEntity.ok().body(mission);
    }

    @PostMapping("/{missionId}/participantes/{adventurerId}")
    public ResponseEntity<MissionDetailsResponseDTO> registerParticipant(@PathVariable Long missionId, @PathVariable Long adventurerId){
        MissionDetailsResponseDTO missionDTO = service.registerParticipant(missionId,adventurerId);
        return ResponseEntity.ok().body(missionDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMissionById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Page<MissionResponseDTO> listingMissions(
            @RequestHeader(value = "X-Page",required = false,defaultValue = "0")
            @Min(value = 0, message = "Page não pode ser negativa")
            int page,
            @RequestHeader(value = "X-Size",required = false, defaultValue = "10")
            @Min(value = 1, message = "Size deve ser no mínimo 1")
            @Max(value = 50, message = "Size deve ser no máximo 50")
            int size,
            @RequestParam(required = false) String missionStatus,
            @RequestParam(required = false) String dangerLevel,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ){
        return service.listMissions(missionStatus,dangerLevel,startDate,endDate,size,page);
    }

    @GetMapping("/{missionId}")
    public ResponseEntity<MissionDetailsResponseDTO> listMission(@PathVariable Long missionId){
        MissionDetailsResponseDTO missionDTO = service.listMissionById(missionId);
        return ResponseEntity.ok().body(missionDTO);
    }

    @GetMapping("/metrics")
    public Page<MissionMetricsResponseDTO> listingMissionMetrics(
            @RequestHeader(value = "X-Page",required = false,defaultValue = "0")
            @Min(value = 0, message = "Page não pode ser negativa")
            int page,
            @RequestHeader(value = "X-Size",required = false, defaultValue = "10")
            @Min(value = 1, message = "Size deve ser no mínimo 1")
            @Max(value = 50, message = "Size deve ser no máximo 50")
            int size
    ){
        return service.listMissionsMetrics(size,page);
    }




}
