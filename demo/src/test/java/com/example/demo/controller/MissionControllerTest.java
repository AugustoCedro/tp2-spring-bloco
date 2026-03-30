package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.mapper.MissionMapper;
import com.example.demo.model.adventure.DangerLevel;
import com.example.demo.model.adventure.Mission;
import com.example.demo.model.adventure.MissionStatus;
import com.example.demo.model.audit.Organization;
import com.example.demo.service.AdventurerService;
import com.example.demo.service.MissionService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MissionController.class)
@Import(MissionMapper.class)
public class MissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MissionService missionService;

    @Autowired
    private MissionMapper missionMapper;

    @Test
    void shouldListMissionById() throws Exception{

        Mission mission = buildMission();

        MissionDetailsResponseDTO dto = missionMapper.toMissionDetailsResponseDTO(mission,null);

        when(missionService.listMissionById(1L)).thenReturn(dto);

        mockMvc.perform(get("/missoes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Missão do Dragão Vermelho"));
    }


    @Test
    void shouldListMissionsWithFilters() throws Exception{
        Mission mission = buildMission();

        MissionResponseDTO dto = missionMapper.toMissionResponseDTO(mission);

        Page<MissionResponseDTO> page = new PageImpl<>(List.of(dto));

        when(missionService.listMissionsWithFilters(
                eq("CONCLUIDA"),
                eq("ALTO"),
                eq(LocalDate.of(2025, 1, 1)),
                eq(LocalDate.of(2025, 12, 31)),
                eq(10),
                eq(0)
        )).thenReturn(page);

        mockMvc.perform(get("/missoes")
                        .header("X-Page",0)
                        .header("X-Size",10)
                        .param("dangerLevel","ALTO")
                        .param("missionStatus","CONCLUIDA")
                        .param("startDate","2025-01-01")
                        .param("endDate","2025-12-31")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Missão do Dragão Vermelho"));
    }


    @Test
    void shouldListMissionsMetrics() throws Exception{
        Mission mission = buildMission();

        MissionMetricsResponseDTO dto =  missionMapper.toMissionMetricsResponseDTO(mission);

        Page<MissionMetricsResponseDTO> page = new PageImpl<>(List.of(dto));

        when(missionService.listMissionsMetrics(
                eq(10),
                eq(0)
        )).thenReturn(page);

        mockMvc.perform(get("/missoes/metrics")
                        .header("X-Page",0)
                        .header("X-Size",10)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Missão do Dragão Vermelho"));
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




}
