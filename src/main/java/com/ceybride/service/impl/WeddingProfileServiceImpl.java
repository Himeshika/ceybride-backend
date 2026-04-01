package com.ceybride.service.impl;

import com.ceybride.dto.wedding.WeddingProfileCreateDTO;
import com.ceybride.dto.wedding.WeddingProfileResponseDTO;
import com.ceybride.dto.wedding.WeddingProfileUpdateDTO;
import com.ceybride.entity.User;
import com.ceybride.entity.WeddingProfile;
import com.ceybride.enums.WeddingStatus;
import com.ceybride.exception.DuplicateResourceException;
import com.ceybride.exception.ResourceNotFoundException;
import com.ceybride.mapper.WeddingProfileMapper;
import com.ceybride.repository.WeddingProfileRepository;
import com.ceybride.service.UserService;
import com.ceybride.service.WeddingProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WeddingProfileServiceImpl implements WeddingProfileService {

    private final WeddingProfileRepository weddingProfileRepository;
    private final UserService userService;

    @Override
    @Transactional
    public WeddingProfileResponseDTO create(WeddingProfileCreateDTO request) {
        User user = userService.getCurrentUserEntity();

        if (weddingProfileRepository.findByUserId(user.getId()).isPresent()) {
            throw new DuplicateResourceException("Wedding profile already exists for this user");
        }

        WeddingProfile profile = WeddingProfile.builder()
                .user(user)
                .build();

        apply(profile, request);
        weddingProfileRepository.save(profile);

        return WeddingProfileMapper.toResponse(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public WeddingProfileResponseDTO getCurrentProfile() {
        return WeddingProfileMapper.toResponse(getCurrentWeddingProfileEntity());
    }

    @Override
    @Transactional
    public WeddingProfileResponseDTO update(WeddingProfileUpdateDTO request) {
        WeddingProfile profile = getCurrentWeddingProfileEntity();
        apply(profile, request);
        weddingProfileRepository.save(profile);
        return WeddingProfileMapper.toResponse(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public WeddingProfile getCurrentWeddingProfileEntity() {
        Long userId = userService.getCurrentUserEntity().getId();
        return weddingProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wedding profile not found"));
    }

    private void apply(WeddingProfile profile, WeddingProfileCreateDTO request) {
        profile.setWeddingTitle(normalizeNullable(request.getWeddingTitle()));
        profile.setBrideName(request.getBrideName().trim());
        profile.setGroomName(request.getGroomName().trim());
        profile.setBridePhone(normalizeNullable(request.getBridePhone()));
        profile.setGroomPhone(normalizeNullable(request.getGroomPhone()));
        profile.setBrideEmail(normalizeNullable(request.getBrideEmail()));
        profile.setGroomEmail(normalizeNullable(request.getGroomEmail()));
        profile.setWeddingType(request.getWeddingType());
        profile.setWeddingDate(request.getWeddingDate());
        profile.setGuestCount(request.getGuestCount());
        profile.setTotalBudget(request.getTotalBudget());
        profile.setVenue(normalizeNullable(request.getVenue()));
        profile.setStatus(request.getStatus() != null ? request.getStatus() : WeddingStatus.PLANNING);
    }

    private String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}