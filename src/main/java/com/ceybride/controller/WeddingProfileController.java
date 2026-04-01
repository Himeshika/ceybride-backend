package com.ceybride.controller;

import com.ceybride.dto.common.ApiResponse;
import com.ceybride.dto.wedding.*;
import com.ceybride.service.WeddingProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wedding")
@RequiredArgsConstructor
public class WeddingProfileController {

    private final WeddingProfileService weddingProfileService;

    @PostMapping
    public ResponseEntity<ApiResponse<WeddingProfileResponseDTO>> create(@Valid @RequestBody WeddingProfileCreateDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Wedding profile created successfully", weddingProfileService.create(request)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<WeddingProfileResponseDTO>> getCurrent() {
        return ResponseEntity.ok(ApiResponse.success("Wedding profile fetched successfully", weddingProfileService.getCurrentProfile()));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<WeddingProfileResponseDTO>> update(@Valid @RequestBody WeddingProfileUpdateDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Wedding profile updated successfully", weddingProfileService.update(request)));
    }
}
