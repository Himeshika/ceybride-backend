package com.ceybride.service;

import com.ceybride.dto.task.TaskResponseDTO;
import com.ceybride.dto.task.TaskSaveDTO;
import com.ceybride.dto.task.TaskStatusUpdateDTO;
import com.ceybride.dto.task.TaskUpdateDTO;

import java.util.List;

public interface TaskService {
    TaskResponseDTO create(TaskSaveDTO request);
    List<TaskResponseDTO> getAll();
    TaskResponseDTO getById(Long id);
    TaskResponseDTO update(Long id, TaskUpdateDTO request);
    TaskResponseDTO updateStatus(Long id, TaskStatusUpdateDTO request);
    void delete(Long id);
    List<TaskResponseDTO> getUpcoming();
}
