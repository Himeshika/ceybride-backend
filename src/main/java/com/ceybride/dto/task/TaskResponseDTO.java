package com.ceybride.dto.task;

import com.ceybride.enums.ResponsibleSide;
import com.ceybride.enums.TaskCategory;
import com.ceybride.enums.TaskPriority;
import com.ceybride.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private TaskCategory category;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate dueDate;
    private ResponsibleSide assignedSide;
    private boolean generated;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
