package com.ceybride.mapper;

import com.ceybride.dto.wedding.WeddingProfileResponseDTO;
import com.ceybride.entity.WeddingProfile;

public final class WeddingProfileMapper {
    private WeddingProfileMapper() {
    }

    public static WeddingProfileResponseDTO toResponse(WeddingProfile profile) {
        return WeddingProfileResponseDTO.builder()
                .id(profile.getId())
                .weddingTitle(profile.getWeddingTitle())
                .brideName(profile.getBrideName())
                .groomName(profile.getGroomName())
                .bridePhone(profile.getBridePhone())
                .groomPhone(profile.getGroomPhone())
                .brideEmail(profile.getBrideEmail())
                .groomEmail(profile.getGroomEmail())
                .weddingType(profile.getWeddingType())
                .weddingDate(profile.getWeddingDate())
                .guestCount(profile.getGuestCount())
                .totalBudget(profile.getTotalBudget())
                .venue(profile.getVenue())
                .status(profile.getStatus())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}
