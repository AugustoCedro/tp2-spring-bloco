package com.example.demo.controller;

import com.example.demo.model.adventure.MissionTacticalPanel;
import com.example.demo.service.MissionTacticalPanelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/missoes")
@AllArgsConstructor
@Validated
public class MissionTacticalPanelController {

    private MissionTacticalPanelService service;


    @GetMapping("/top15dias")
    public ResponseEntity<List<MissionTacticalPanel>> listTopMissionsLast15Days(){
        List<MissionTacticalPanel> latestMissions = service.listTopMissionsLast15Days();

        return ResponseEntity.ok().body(latestMissions);
    }



}
