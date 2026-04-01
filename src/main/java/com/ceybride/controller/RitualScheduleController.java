package com.ceybride.controller;

import com.ceybride.dto.common.ApiResponse;
import com.ceybride.dto.ritual.*;
import com.ceybride.service.RitualScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rituals")
@RequiredArgsConstructor
public class RitualScheduleController {

    private final RitualScheduleService ritualScheduleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RitualResponseDTO>> create(@Valid @RequestBody RitualSaveDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Ritual created successfully", ritualScheduleService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RitualResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Rituals fetched successfully", ritualScheduleService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RitualResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Ritual fetched successfully", ritualScheduleService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RitualResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody RitualUpdateDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Ritual updated successfully", ritualScheduleService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        ritualScheduleService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Ritual deleted successfully", null));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<ApiResponse<List<RitualResponseDTO>>> upcoming() {
        return ResponseEntity.ok(ApiResponse.success("Upcoming rituals fetched successfully", ritualScheduleService.getUpcoming()));
    }
}
