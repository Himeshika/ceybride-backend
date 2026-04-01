package com.ceybride.controller;

import com.ceybride.dto.common.ApiResponse;
import com.ceybride.dto.vendor.*;
import com.ceybride.enums.BookingStatus;
import com.ceybride.enums.VendorCategory;
import com.ceybride.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping
    public ResponseEntity<ApiResponse<VendorResponseDTO>> create(@Valid @RequestBody VendorSaveDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Vendor created successfully", vendorService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VendorResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Vendors fetched successfully", vendorService.getAll()));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<VendorResponseDTO>>> filter(
            @RequestParam(required = false) VendorCategory category,
            @RequestParam(required = false) BookingStatus bookingStatus) {
        return ResponseEntity.ok(ApiResponse.success("Vendors filtered successfully", vendorService.filter(category, bookingStatus)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VendorResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Vendor fetched successfully", vendorService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VendorResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody VendorUpdateDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Vendor updated successfully", vendorService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        vendorService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Vendor deleted successfully", null));
    }
}
