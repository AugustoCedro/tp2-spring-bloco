package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.exceptions.*;
import com.example.demo.mapper.AdventurerMapper;
import com.example.demo.mapper.MissionMapper;
import com.example.demo.model.adventure.*;
import com.example.demo.model.audit.Organization;
import com.example.demo.repository.AdventurerRepository;
import com.example.demo.repository.MissionParticipationRepository;
import com.example.demo.repository.MissionRepository;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.util.DateRandomizer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

@Service
@AllArgsConstructor
public class MissionService {

    private OrganizationRepository organizationRepository;
    private AdventurerRepository adventurerRepository;
    private MissionRepository missionRepository;
    private MissionParticipationRepository missionParticipationRepository;
    private static final Random random = new Random();
    private final MissionMapper missionMapper;
    private final AdventurerMapper adventurerMapper;

    public Mission register(MissionRequestDTO dto) {
        Organization organization = organizationRepository
                .findByNameIgnoreCase(dto.organization())
                .orElseThrow(() -> new OrganizationNotFoundException("organization not found"));

        missionRepository.findByTitleIgnoreCase(dto.title()).ifPresent(m ->
        {
            throw new MissionAlreadyRegisteredException("Mission Name already registered");
        });

        Mission mission = new Mission(organization, dto.title(), generateRandomDangerLevel(),generateRandomMissionStatus(), DateRandomizer.randomDate());
        organization.getMissions().add(mission);
        missionRepository.save(mission);
        return mission;
    }

    public void deleteById(Long id) {
        missionRepository.deleteById(id);
    }

    public MissionDetailsResponseDTO registerParticipant(Long missionId, Long adventurerId) {
        Mission mission = missionRepository
                .findById(missionId)
                .orElseThrow(() -> new MissionNotFoundException("Mission not found"));

        validateMissionStatus(mission);

        Adventurer adventurer = adventurerRepository
                .findByIdAndOrganizationId(adventurerId, mission.getOrganization().getId())
                .orElseThrow(() -> new AdventurerNotFoundException("Adventurer not found in this organization"));

        validateAdventurerActive(adventurer);

        mission.getParticipations()
                .stream()
                .filter(p -> p.getAdventurer().equals(adventurer))
                .findFirst()
                .ifPresent(p -> {
                    throw new AdventurerAlreadyInThisMissionException("Adventurer already in this mission");
                });

        MissionParticipation participation = new MissionParticipation(mission,adventurer,generateRandomMissionRole(), random.nextInt(1500) + 1);

        mission.getParticipations().add(participation);
        adventurer.getParticipations().add(participation);
        missionParticipationRepository.save(participation);

        return missionMapper.toMissionDetailsResponseDTO(mission,adventurerMapper);
    }


    public Page<MissionResponseDTO> listMissionsWithFilters(String missionStatus, String dangerLevel, LocalDate startDate,LocalDate endDate,int size,int page) {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("startedAt").ascending());

        DangerLevel dangerLevelEnum = dangerLevel != null ?
                Arrays.stream(DangerLevel.values())
                .filter(d -> d.name().equalsIgnoreCase(dangerLevel))
                .findFirst().orElseThrow(() -> new InvalidEnumValueException("DangerLevel",
                                Arrays
                                        .stream(DangerLevel.values())
                                        .map(Enum::name)
                                        .toList()))
                : null;

        MissionStatus missionStatusEnum = missionStatus != null ?
                Arrays.stream(MissionStatus.values())
                        .filter(m -> m.name().equalsIgnoreCase(missionStatus))
                        .findFirst().orElseThrow(() -> new InvalidEnumValueException("Mission Status",
                                Arrays
                                        .stream(MissionStatus.values())
                                        .map(Enum::name)
                                        .toList()))
                : null;

        Page<Mission> missions  = missionRepository.findWithFilters(missionStatusEnum,dangerLevelEnum,startDate,endDate,pageRequest);

        return missions.map(missionMapper::toMissionResponseDTO);
    }

    public Page<MissionMetricsResponseDTO> listMissionsMetrics(int size,int page){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("startedAt").ascending());

        Page<Mission> missions  = missionRepository.findAll(pageRequest);
        return missions.map(missionMapper::toMissionMetricsResponseDTO);

    }

    public MissionDetailsResponseDTO listMissionById(Long missionId) {
        Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new MissionNotFoundException("Mission not Found"));
        return missionMapper.toMissionDetailsResponseDTO(mission,adventurerMapper);
    }

    private DangerLevel generateRandomDangerLevel(){
        DangerLevel[] values = DangerLevel.values();
        return values[random.nextInt(values.length)];
    }
    private MissionStatus generateRandomMissionStatus(){
        MissionStatus[] values = MissionStatus.values();
        return values[random.nextInt(values.length)];
    }
    private MissionRole generateRandomMissionRole(){
        MissionRole[] values = MissionRole.values();
        return values[random.nextInt(values.length)];
    }
    private void validateMissionStatus(Mission mission){
        if(mission.getMissionStatus() != MissionStatus.EM_ANDAMENTO && mission.getMissionStatus() != MissionStatus.PLANEJADA){
            throw new MissionStatusAdventurerException("Cannot add adventurer in this mission status");
        }
    }
    private void validateAdventurerActive(Adventurer adventurer) {
        if (!adventurer.getActive()) {
            throw new AdventurerIsInactiveException("Adventurer is inactive");
        }
    }

    public MissionResponseDTO getAdventurerLastMission(Long adventurerId){
        return missionParticipationRepository
                .findTopByAdventurerIdOrderByMissionCreatedAtDesc(adventurerId)
                .map(p -> missionMapper.toMissionResponseDTO(p.getMission()))
                .orElse(null);
    }


}
