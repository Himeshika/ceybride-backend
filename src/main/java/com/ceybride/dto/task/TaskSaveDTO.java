package com.ceybride.dto.task;

import com.ceybride.enums.ResponsibleSide;
import com.ceybride.enums.TaskCategory;
import com.ceybride.enums.TaskPriority;
import com.ceybride.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskSaveDTO {

    @NotBlank
    @Size(max = 150)
    private String title;

    @Size(max = 500)
    private String description;

    private TaskCategory category;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate dueDate;
    private ResponsibleSide assignedSide;
}
