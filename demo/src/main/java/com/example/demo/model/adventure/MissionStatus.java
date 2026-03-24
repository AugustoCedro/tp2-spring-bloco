package com.example.demo.model.adventure;

import java.time.LocalDateTime;

public enum MissionStatus {
    PLANEJADA{
        @Override
        public void apply(Mission mission) {

        }
    },
    EM_ANDAMENTO {
        @Override
        public void apply(Mission mission){
            mission.setStartedAt(mission.getCreatedAt().plusWeeks(1));
        }
    },
    CONCLUIDA{
        @Override
        public void apply(Mission mission){
            mission.setStartedAt(mission.getCreatedAt().plusWeeks(1));
            mission.setFinishedAt(mission.getCreatedAt().plusWeeks(3));
        }
    },
    CANCELADA{
        @Override
        public void apply(Mission mission) {

        }
    };

    public abstract void apply(Mission mission);
}
