package com.ceybride.controller;

import com.ceybride.dto.auth.*;
import com.ceybride.dto.common.ApiResponse;
import com.ceybride.service.AuthService;
import com.ceybride.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Login successful", authService.login(request)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDTO>> me() {
        return ResponseEntity.ok(ApiResponse.success("Current user fetched successfully", userService.getCurrentUser()));
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordDTO request) {
        userService.changePassword(request);
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
    }
}
