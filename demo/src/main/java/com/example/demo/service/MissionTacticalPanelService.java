package com.example.demo.service;

import com.example.demo.model.adventure.MissionTacticalPanel;
import com.example.demo.repository.MissionTacticalPanelRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class MissionTacticalPanelService {

    private MissionTacticalPanelRepository repository;

    @Cacheable("topMissions")
    public List<MissionTacticalPanel> listTopMissionsLast15Days() {
        List<MissionTacticalPanel> missionPanelList = repository.findAll();
        System.out.println(missionPanelList);
        return missionPanelList.stream()
                .filter(m -> m.getUpdatedAt().isAfter(LocalDate.now().minusDays(15).atStartOfDay()))
                .sorted(Comparator.comparing(MissionTacticalPanel::getReadinessIndex).reversed())
                .limit(10)
                .toList();
    }

    @Scheduled(fixedRate = 60000)
    @CacheEvict(value = "topMissions", allEntries = true)
    public void evictCache() {
        System.out.println("Cache limpo");
    }



}
