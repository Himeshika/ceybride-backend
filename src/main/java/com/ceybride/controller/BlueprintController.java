package com.ceybride.controller;

import com.ceybride.dto.common.ApiResponse;
import com.ceybride.dto.wedding.*;
import com.ceybride.service.BlueprintGeneratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blueprint")
@RequiredArgsConstructor
public class BlueprintController {

    private final BlueprintGeneratorService blueprintGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<BlueprintGenerateResponseDTO>> generate(@Valid @RequestBody BlueprintGenerateRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Wedding blueprint generated successfully", blueprintGeneratorService.generate(request)));
    }

    @PostMapping("/regenerate")
    public ResponseEntity<ApiResponse<BlueprintGenerateResponseDTO>> regenerate(@Valid @RequestBody BlueprintGenerateRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Wedding blueprint regenerated successfully", blueprintGeneratorService.regenerate(request)));
    }

    @GetMapping("/preview")
    public ResponseEntity<ApiResponse<BlueprintPreviewDTO>> preview() {
        return ResponseEntity.ok(ApiResponse.success("Blueprint preview fetched successfully", blueprintGeneratorService.preview()));
    }
}
