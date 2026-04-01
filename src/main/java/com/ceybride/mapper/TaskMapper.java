package com.ceybride.mapper;

import com.ceybride.dto.task.TaskResponseDTO;
import com.ceybride.entity.Task;

public final class TaskMapper {
    private TaskMapper() {
    }

    public static TaskResponseDTO toResponse(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .category(task.getCategory())
                .priority(task.getPriority())
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .assignedSide(task.getAssignedSide())
                .generated(task.isGenerated())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
