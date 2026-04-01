package com.ceybride.dto.wedding;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlueprintGenerateResponseDTO {
    private WeddingProfileResponseDTO weddingProfile;
    private int generatedTaskCount;
    private int generatedExpenseCount;
    private int generatedRitualCount;
}
