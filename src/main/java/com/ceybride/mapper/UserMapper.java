package com.ceybride.mapper;

import com.ceybride.dto.auth.UserResponseDTO;
import com.ceybride.entity.User;

public final class UserMapper {
    private UserMapper() {
    }

    public static UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }
}
