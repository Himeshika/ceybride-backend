package com.ceybride.dto.ritual;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RitualResponseDTO {
    private Long id;
    private String ritualName;
    private LocalDateTime eventDateTime;
    private String luckyColor;
    private String facingDirection;
    private String notes;
    private Integer sequenceOrder;
    private boolean completed;
    private boolean generated;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
