package com.ceybride.dto.wedding;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BlueprintPreviewDTO {
    private String weddingType;
    private int taskTemplateCount;
    private int expenseTemplateCount;
    private int ritualTemplateCount;
    private List<String> sampleTasks;
    private List<String> sampleExpenses;
    private List<String> sampleRituals;
}
