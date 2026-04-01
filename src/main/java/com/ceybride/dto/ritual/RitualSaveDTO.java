package com.ceybride.dto.ritual;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RitualSaveDTO {

    @NotBlank
    @Size(max = 150)
    private String ritualName;

    private LocalDateTime eventDateTime;

    @Size(max = 50)
    private String luckyColor;

    @Size(max = 50)
    private String facingDirection;

    @Size(max = 500)
    private String notes;

    private Integer sequenceOrder;
    private Boolean completed;
}
