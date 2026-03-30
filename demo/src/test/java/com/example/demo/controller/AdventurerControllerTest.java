package com.example.demo.controller;

import com.example.demo.dto.AdventurerDetailsResponseDTO;
import com.example.demo.dto.AdventurerResponseDTO;
import com.example.demo.dto.AdventurerSearchResponseDTO;
import com.example.demo.model.adventure.Category;
import com.example.demo.service.AdventurerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdventurerController.class)
public class AdventurerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdventurerService adventurerService;

    @Test
    void shouldListAdventurersWithFilters() throws Exception {

        AdventurerResponseDTO dto = new AdventurerResponseDTO(
                1L,
                "João",
                Category.GUERREIRO,
                20,
                true
        );

        Page<AdventurerResponseDTO> page = new PageImpl<>(List.of(dto));

        when(adventurerService.listAdventurers(
                eq(true),
                eq("GUERREIRO"),
                eq(10),
                eq(10),
                eq(0)
        )).thenReturn(page);

        mockMvc.perform(get("/aventureiros")
                        .header("X-Page",0)
                        .header("X-Size",10)
                        .param("active","true")
                        .param("category","GUERREIRO")
                        .param("minLevel","10")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("João"))
                .andExpect(jsonPath("$.content[0].level").value(20));
    }

    @Test
    void shouldReturnAdventurerById() throws Exception {

        AdventurerDetailsResponseDTO dto = new AdventurerDetailsResponseDTO(
                1L,
                null,
                "Augusto",
                null,
                10,
                true,
                null,
                null,
                null,
                0,
                null
        );

        when(adventurerService.listAdventurerById(1L)).thenReturn(dto);

        mockMvc.perform(get("/aventureiros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Augusto"))
                .andExpect(jsonPath("$.level").value(10));
    }

    @Test
    void shouldListAdventurersByName() throws Exception{

        AdventurerSearchResponseDTO dto = new AdventurerSearchResponseDTO(
                1L,
                "Maria",
                true,
                null,
                null
        );

        AdventurerSearchResponseDTO dto2 = new AdventurerSearchResponseDTO(
                2L,
                "Marcelo",
                true,
                null,
                null
        );

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
                .andExpect(jsonPath("$.content[0].name").value("Maria"))
                .andExpect(jsonPath("$.content[1].name").value("Marcelo"));
    }





}
