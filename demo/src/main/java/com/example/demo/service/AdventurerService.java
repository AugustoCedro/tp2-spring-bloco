package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.mapper.AdventurerMapper;
import com.example.demo.model.adventure.*;
import com.example.demo.model.audit.Organization;
import com.example.demo.model.audit.User;
import com.example.demo.repository.AdventurerRepository;
import com.example.demo.repository.CompanionRepository;
import com.example.demo.repository.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AdventurerService {

    private AdventurerRepository adventurerRepository;
    private OrganizationRepository organizationRepository;
    private CompanionRepository companionRepository;
    private static final Random random = new Random();
    private AdventurerMapper adventurerMapper;
    private MissionService missionService;

    public Adventurer register(AdventurerRequestDTO dto) {
        Organization organization = organizationRepository
                .findByNameIgnoreCase(dto.organization())
                .orElseThrow(() -> new IllegalArgumentException("organization not found"));

        User user = organization.getUsers().stream()
                .filter(u -> u.getEmail().equals(dto.user()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User" + dto.user() + " not found"));

        Adventurer adventurer = new Adventurer(dto.level(), Category.valueOf(dto.category().toUpperCase()),dto.name(),user,organization);
        organization.getAdventurers().add(adventurer);
        adventurerRepository.save(adventurer);
        return adventurer;
    }

    public Page<AdventurerResponseDTO> listAdventurers(Boolean active, String category, Integer minLevel, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("level").ascending());

        Category categoryEnum = category != null ?
                Arrays.stream(Category.values())
                        .filter(d -> d.name().equalsIgnoreCase(category))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid Category")) : null;

        Page<Adventurer> adventurers = adventurerRepository.findWithFilters(active,categoryEnum,minLevel,pageRequest);

        return adventurers.map(a -> adventurerMapper.toAdventurerResponseDTO(a));
    }

    public Page<AdventurerSearchResponseDTO> listAdventurersByName(String name, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page,size,Sort.by("level").ascending());

        Page<Adventurer> adventurers = adventurerRepository.findByNameContainingIgnoreCase(name,pageRequest);

        return adventurers.map(a -> adventurerMapper.toAdventurerSearchResponseDTO(a));
    }

    public AdventurerSearchResponseDTO registerCompanion(Long adventurerId, CompanionRequestDTO dto) {
       Adventurer adventurer = adventurerRepository.findById(adventurerId).orElseThrow(() -> new IllegalArgumentException("Adventurer not found"));

        Optional.ofNullable(adventurer.getCompanion())
                .ifPresent(c -> {
                    throw new IllegalArgumentException("Adventurer already has a companion");
                });

        Specie specie = Arrays.stream(Specie.values())
                .filter(s -> s.name()
                .equalsIgnoreCase(dto.specie()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid specie"));

        Companion companion = new Companion(dto.name(),specie, random.nextInt(100),adventurer);
        adventurer.setCompanion(companion);
        adventurer.setUpdatedAt(LocalDateTime.now());
        companionRepository.save(companion);

        return adventurerMapper.toAdventurerSearchResponseDTO(adventurer);
    }

    public AdventurerDetailsResponseDTO listAdventurerById(Long adventurerId){
        Adventurer adventurer = adventurerRepository.findById(adventurerId).orElseThrow(() -> new IllegalArgumentException("Adventurer not found"));
        MissionResponseDTO missionDTO = missionService.getAdventurerLastMission(adventurerId);
        return adventurerMapper.toAdventurerDetailsResponseDTO(adventurer,missionDTO);
    }


    public Page<AdventurerDetailsResponseDTO> listAdventurersRanking(String missionStatus, LocalDate startDate, LocalDate endDate, int size, int page) {
        PageRequest pageRequest = PageRequest.of(page,size);

        MissionStatus missionStatusEnum = missionStatus != null ?
                Arrays.stream(MissionStatus.values())
                        .filter(m -> m.name().equalsIgnoreCase(missionStatus))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid MissionStatus")) : null;

        Page<Adventurer> adventurers = adventurerRepository.findRanking(missionStatusEnum,startDate,endDate,pageRequest);
        return adventurers.map(a -> adventurerMapper.toAdventurerDetailsResponseDTO(a,missionService.getAdventurerLastMission(a.getId())));
    }
}
