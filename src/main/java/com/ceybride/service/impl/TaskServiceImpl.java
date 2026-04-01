package com.ceybride.service.impl;

import com.ceybride.dto.task.TaskResponseDTO;
import com.ceybride.dto.task.TaskSaveDTO;
import com.ceybride.dto.task.TaskStatusUpdateDTO;
import com.ceybride.dto.task.TaskUpdateDTO;
import com.ceybride.entity.Task;
import com.ceybride.entity.WeddingProfile;
import com.ceybride.enums.ResponsibleSide;
import com.ceybride.enums.TaskCategory;
import com.ceybride.enums.TaskPriority;
import com.ceybride.enums.TaskStatus;
import com.ceybride.exception.ResourceNotFoundException;
import com.ceybride.mapper.TaskMapper;
import com.ceybride.repository.TaskRepository;
import com.ceybride.service.TaskService;
import com.ceybride.service.WeddingProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final WeddingProfileService weddingProfileService;

    @Override
    @Transactional
    public TaskResponseDTO create(TaskSaveDTO request) {
        WeddingProfile profile = weddingProfileService.getCurrentWeddingProfileEntity();

        Task task = Task.builder()
                .weddingProfile(profile)
                .isGenerated(false)
                .build();

        apply(task, request);
        taskRepository.save(task);

        return TaskMapper.toResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAll() {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();
        return taskRepository.findByWeddingProfileIdOrderByDueDateAscCreatedAtDesc(weddingId)
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO getById(Long id) {
        return TaskMapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public TaskResponseDTO update(Long id, TaskUpdateDTO request) {
        Task task = getEntity(id);
        apply(task, request);
        taskRepository.save(task);
        return TaskMapper.toResponse(task);
    }

    @Override
    @Transactional
    public TaskResponseDTO updateStatus(Long id, TaskStatusUpdateDTO request) {
        Task task = getEntity(id);
        task.setStatus(request.getStatus());
        taskRepository.save(task);
        return TaskMapper.toResponse(task);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.delete(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getUpcoming() {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();
        return taskRepository.findTop5ByWeddingProfileIdAndDueDateGreaterThanEqualOrderByDueDateAsc(
                        weddingId,
                        LocalDate.now()
                )
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    private Task getEntity(Long id) {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();
        return taskRepository.findByIdAndWeddingProfileId(id, weddingId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    private void apply(Task task, TaskSaveDTO request) {
        task.setTitle(request.getTitle().trim());
        task.setDescription(normalizeNullable(request.getDescription()));
        task.setCategory(request.getCategory() != null ? request.getCategory() : TaskCategory.GENERAL);
        task.setPriority(request.getPriority() != null ? request.getPriority() : TaskPriority.MEDIUM);
        task.setStatus(request.getStatus() != null ? request.getStatus() : TaskStatus.PENDING);
        task.setDueDate(request.getDueDate());
        task.setAssignedSide(request.getAssignedSide() != null ? request.getAssignedSide() : ResponsibleSide.SHARED);
    }

    private String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}