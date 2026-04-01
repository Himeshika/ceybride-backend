package com.ceybride.controller;

import com.ceybride.dto.common.ApiResponse;
import com.ceybride.dto.task.*;
import com.ceybride.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponseDTO>> create(@Valid @RequestBody TaskSaveDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Task created successfully", taskService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Tasks fetched successfully", taskService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Task fetched successfully", taskService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody TaskUpdateDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Task updated successfully", taskService.update(id, request)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> updateStatus(@PathVariable Long id, @Valid @RequestBody TaskStatusUpdateDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Task status updated successfully", taskService.updateStatus(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Task deleted successfully", null));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> upcoming() {
        return ResponseEntity.ok(ApiResponse.success("Upcoming tasks fetched successfully", taskService.getUpcoming()));
    }
}
