package com.ceybride.service.impl;

import com.ceybride.dto.auth.AuthResponseDTO;
import com.ceybride.dto.auth.LoginRequestDTO;
import com.ceybride.dto.auth.RegisterRequestDTO;
import com.ceybride.entity.User;
import com.ceybride.enums.Role;
import com.ceybride.exception.DuplicateResourceException;
import com.ceybride.mapper.UserMapper;
import com.ceybride.repository.UserRepository;
import com.ceybride.security.JwtService;
import com.ceybride.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO request) {
        String email = normalizeEmail(request.getEmail());

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName().trim())
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(normalizeNullable(request.getPhone()))
                .role(Role.USER)
                .isActive(true)
                .build();

        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return AuthResponseDTO.builder()
                .token(token)
                .tokenType("Bearer")
                .user(UserMapper.toResponse(user))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponseDTO login(LoginRequestDTO request) {
        String email = normalizeEmail(request.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.getPassword())
        );

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return AuthResponseDTO.builder()
                .token(token)
                .tokenType("Bearer")
                .user(UserMapper.toResponse(user))
                .build();
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    private String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}