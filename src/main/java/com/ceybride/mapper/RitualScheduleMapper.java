package com.ceybride.mapper;

import com.ceybride.dto.ritual.RitualResponseDTO;
import com.ceybride.entity.RitualSchedule;

public final class RitualScheduleMapper {
    private RitualScheduleMapper() {
    }

    public static RitualResponseDTO toResponse(RitualSchedule ritual) {
        return RitualResponseDTO.builder()
                .id(ritual.getId())
                .ritualName(ritual.getRitualName())
                .eventDateTime(ritual.getEventDateTime())
                .luckyColor(ritual.getLuckyColor())
                .facingDirection(ritual.getFacingDirection())
                .notes(ritual.getNotes())
                .sequenceOrder(ritual.getSequenceOrder())
                .completed(ritual.isCompleted())
                .generated(ritual.isGenerated())
                .createdAt(ritual.getCreatedAt())
                .updatedAt(ritual.getUpdatedAt())
                .build();
    }
}
