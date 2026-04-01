package com.ceybride.controller;

import com.ceybride.dto.common.ApiResponse;
import com.ceybride.dto.guest.*;
import com.ceybride.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping
    public ResponseEntity<ApiResponse<GuestResponseDTO>> create(@Valid @RequestBody GuestSaveDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Guest created successfully", guestService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GuestResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Guests fetched successfully", guestService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GuestResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Guest fetched successfully", guestService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GuestResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody GuestUpdateDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Guest updated successfully", guestService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        guestService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Guest deleted successfully", null));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<GuestStatsDTO>> stats() {
        return ResponseEntity.ok(ApiResponse.success("Guest stats fetched successfully", guestService.getStats()));
    }
}
