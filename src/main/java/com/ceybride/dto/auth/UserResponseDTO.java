package com.ceybride.dto.auth;

import com.ceybride.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
    private boolean active;
}
