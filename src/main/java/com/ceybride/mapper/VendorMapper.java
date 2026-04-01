package com.ceybride.mapper;

import com.ceybride.dto.vendor.VendorResponseDTO;
import com.ceybride.entity.Vendor;

public final class VendorMapper {
    private VendorMapper() {
    }

    public static VendorResponseDTO toResponse(Vendor vendor) {
        return VendorResponseDTO.builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .category(vendor.getCategory())
                .phone(vendor.getPhone())
                .email(vendor.getEmail())
                .address(vendor.getAddress())
                .estimatedCost(vendor.getEstimatedCost())
                .actualCost(vendor.getActualCost())
                .bookingStatus(vendor.getBookingStatus())
                .notes(vendor.getNotes())
                .createdAt(vendor.getCreatedAt())
                .updatedAt(vendor.getUpdatedAt())
                .build();
    }
}
