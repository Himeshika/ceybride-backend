package com.ceybride.mapper;

import com.ceybride.dto.guest.GuestResponseDTO;
import com.ceybride.entity.Guest;

public final class GuestMapper {
    private GuestMapper() {
    }

    public static GuestResponseDTO toResponse(Guest guest) {
        return GuestResponseDTO.builder()
                .id(guest.getId())
                .fullName(guest.getFullName())
                .phone(guest.getPhone())
                .email(guest.getEmail())
                .guestSide(guest.getGuestSide())
                .rsvpStatus(guest.getRsvpStatus())
                .groupType(guest.getGroupType())
                .tableNumber(guest.getTableNumber())
                .notes(guest.getNotes())
                .createdAt(guest.getCreatedAt())
                .updatedAt(guest.getUpdatedAt())
                .build();
    }
}
