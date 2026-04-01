package com.ceybride.dto.task;

import com.ceybride.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskStatusUpdateDTO {

    @NotNull
    private TaskStatus status;
}
