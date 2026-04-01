package com.ceybride.service;

import com.ceybride.dto.auth.AuthResponseDTO;
import com.ceybride.dto.auth.LoginRequestDTO;
import com.ceybride.dto.auth.RegisterRequestDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
}
