package com.ceybride.service.impl;

import com.ceybride.dto.auth.ChangePasswordDTO;
import com.ceybride.dto.auth.UserResponseDTO;
import com.ceybride.entity.User;
import com.ceybride.exception.BadRequestException;
import com.ceybride.exception.UnauthorizedException;
import com.ceybride.mapper.UserMapper;
import com.ceybride.repository.UserRepository;
import com.ceybride.service.UserService;
import com.ceybride.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUserEntity() {
        String email = SecurityUtils.getCurrentUsername();
        if (email == null) {
            throw new UnauthorizedException("No authenticated user found");
        }

        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UnauthorizedException("Authenticated user not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getCurrentUser() {
        return UserMapper.toResponse(getCurrentUserEntity());
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDTO request) {
        User user = getCurrentUserEntity();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new UnauthorizedException("Current password is incorrect");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new BadRequestException("New password must be different from the current password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}