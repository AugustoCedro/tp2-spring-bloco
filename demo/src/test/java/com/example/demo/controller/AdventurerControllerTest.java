package com.example.demo.controller;

import com.example.demo.dto.AdventurerDetailsResponseDTO;
import com.example.demo.dto.AdventurerResponseDTO;
import com.example.demo.dto.AdventurerSearchResponseDTO;
import com.example.demo.dto.MissionResponseDTO;
import com.example.demo.mapper.AdventurerMapper;
import com.example.demo.mapper.CompanionMapper;
import com.example.demo.mapper.MissionMapper;
import com.example.demo.mapper.OrganizationMapper;
import com.example.demo.model.adventure.*;
import com.example.demo.model.audit.Organization;
import com.example.demo.service.AdventurerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdventurerController.class)
@Import({AdventurerMapper.class, MissionMapper.class})
public class AdventurerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdventurerService adventurerService;

    @Autowired
    private AdventurerMapper adventurerMapper;

    @Autowired
    private MissionMapper missionMapper;

    @MockBean
    private CompanionMapper companionMapper;

    @MockBean
    private OrganizationMapper organizationMapper;

    @Test
    void shouldListAdventurersWithFilters() throws Exception {

        Adventurer adventurer = buildAdventurer();

        AdventurerResponseDTO dto = adventurerMapper.toAdventurerResponseDTO(adventurer);

        Page<AdventurerResponseDTO> page = new PageImpl<>(List.of(dto));

        when(adventurerService.listAdventurersWithFilters(
                eq(true),
                eq("ARQUEIRO"),
                eq(10),
                eq(10),
                eq(0)
        )).thenReturn(page);

        mockMvc.perform(get("/aventureiros")
                        .header("X-Page",0)
                        .header("X-Size",10)
                        .param("active","true")
                        .param("category","ARQUEIRO")
                        .param("minLevel","10")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Marcelo"))
                .andExpect(jsonPath("$.content[0].level").value(100));
    }

    @Test
    void shouldReturnAdventurerById() throws Exception {

        Adventurer adventurer = buildAdventurer();
        Mission mission = buildMission();
        AdventurerDetailsResponseDTO dto = adventurerMapper.toAdventurerDetailsResponseDTO(adventurer, missionMapper.toMissionResponseDTO(mission));

        when(adventurerService.listAdventurerById(1L)).thenReturn(dto);

        mockMvc.perform(get("/aventureiros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Marcelo"))
                .andExpect(jsonPath("$.level").value(100));
    }

    @Test
    void shouldListAdventurersByName() throws Exception{

        Adventurer adventurer = buildAdventurer();
        Adventurer adventurer2 = buildAdventurer();
        adventurer2.setName("Maria");
        AdventurerSearchResponseDTO dto = adventurerMapper.toAdventurerSearchResponseDTO(adventurer);
        AdventurerSearchResponseDTO dto2 = adventurerMapper.toAdventurerSearchResponseDTO(adventurer2);

        Page<AdventurerSearchResponseDTO> page = new PageImpl<>(List.of(dto,dto2));

        when(adventurerService.listAdventurersByName(
                eq("Mar"),
                eq(10),
                eq(0)
        )).thenReturn(page);

        mockMvc.perform(get("/aventureiros/buscar")
                        .header("X-Page",0)
                        .header("X-Size",10)
                        .param("name","Mar")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Marcelo"))
                .andExpect(jsonPath("$.content[1].name").value("Maria"));
    }

    @Test
    void shouldListAdventurersRanking() throws Exception{
        Adventurer adventurer = buildAdventurer();
        adventurer.setId(1L);

        Mission mission = buildMission();

        MissionParticipation missionParticipation = new MissionParticipation(
                mission,
                adventurer,
                MissionRole.DPS,
                1000
        );

        mission.setParticipations(List.of(missionParticipation));
        adventurer.setParticipations(List.of(missionParticipation));

        AdventurerDetailsResponseDTO dto = adventurerMapper.toAdventurerDetailsResponseDTO(adventurer,missionMapper.toMissionResponseDTO(mission));

        Page<AdventurerDetailsResponseDTO> page = new PageImpl<>(List.of(dto));

        when(adventurerService.listAdventurersRanking(
                eq("CONCLUIDA"),
                eq(LocalDate.of(2025, 1, 1)),
                eq(LocalDate.of(2025, 12, 31)),
                eq(10),
                eq(0)
        )).thenReturn(page);

        mockMvc.perform(get("/aventureiros/ranking")
                        .header("X-Page",0)
                        .header("X-Size",10)
                        .param("missionStatus","CONCLUIDA")
                        .param("startDate","2025-01-01")
                        .param("endDate","2025-12-31")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Marcelo"));
    }

    private Mission buildMission() {
        Organization organization = new Organization(
                1L,
                "Guilda do Nordeste",
                true,
                null, null, null, null, null, null
        );

        Mission mission = new Mission(
                organization,
                "Missão do Dragão Vermelho",
                DangerLevel.ALTO,
                MissionStatus.CONCLUIDA,
                LocalDate.of(2025, 2, 1)
        );

        mission.setParticipations(List.of());
        return mission;
    }

    private Adventurer buildAdventurer(){
        Organization organization = new Organization(
                1L,
                "Guilda do Nordeste",
                true,
                null, null, null, null, null, null
        );

        Adventurer adventurer = new Adventurer(
                100,
                Category.ARQUEIRO,
                "Marcelo",
                null,
                organization
        );

        adventurer.setParticipations(List.of());
        return adventurer;
    }





}
