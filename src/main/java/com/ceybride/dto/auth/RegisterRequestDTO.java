package com.ceybride.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 120)
    private String fullName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 8, max = 100, message = "Password must be 8-100 characters")
    @NotBlank(message = "Password is required")
    private String password;

    @Size(max = 20)
    private String phone;
}
