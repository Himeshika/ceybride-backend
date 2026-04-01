package com.ceybride.service.impl;

import com.ceybride.dto.guest.GuestResponseDTO;
import com.ceybride.dto.guest.GuestSaveDTO;
import com.ceybride.dto.guest.GuestStatsDTO;
import com.ceybride.dto.guest.GuestUpdateDTO;
import com.ceybride.entity.Guest;
import com.ceybride.entity.WeddingProfile;
import com.ceybride.enums.GuestSide;
import com.ceybride.enums.RsvpStatus;
import com.ceybride.exception.BadRequestException;
import com.ceybride.exception.ResourceNotFoundException;
import com.ceybride.mapper.GuestMapper;
import com.ceybride.repository.GuestRepository;
import com.ceybride.service.GuestService;
import com.ceybride.service.WeddingProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final WeddingProfileService weddingProfileService;

    @Override
    @Transactional
    public GuestResponseDTO create(GuestSaveDTO request) {
        WeddingProfile profile = weddingProfileService.getCurrentWeddingProfileEntity();

        Guest guest = Guest.builder()
                .weddingProfile(profile)
                .build();

        apply(guest, request);
        guestRepository.save(guest);

        return GuestMapper.toResponse(guest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestResponseDTO> getAll() {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();
        return guestRepository.findByWeddingProfileIdOrderByCreatedAtDesc(weddingId)
                .stream()
                .map(GuestMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GuestResponseDTO getById(Long id) {
        return GuestMapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public GuestResponseDTO update(Long id, GuestUpdateDTO request) {
        Guest guest = getEntity(id);
        apply(guest, request);
        guestRepository.save(guest);
        return GuestMapper.toResponse(guest);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        guestRepository.delete(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public GuestStatsDTO getStats() {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();

        return GuestStatsDTO.builder()
                .totalGuests(guestRepository.countByWeddingProfileId(weddingId))
                .brideSideCount(guestRepository.countByWeddingProfileIdAndGuestSide(weddingId, GuestSide.BRIDE_SIDE))
                .groomSideCount(guestRepository.countByWeddingProfileIdAndGuestSide(weddingId, GuestSide.GROOM_SIDE))
                .confirmedCount(guestRepository.countByWeddingProfileIdAndRsvpStatus(weddingId, RsvpStatus.CONFIRMED))
                .pendingCount(guestRepository.countByWeddingProfileIdAndRsvpStatus(weddingId, RsvpStatus.PENDING))
                .declinedCount(guestRepository.countByWeddingProfileIdAndRsvpStatus(weddingId, RsvpStatus.DECLINED))
                .build();
    }

    private Guest getEntity(Long id) {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();
        return guestRepository.findByIdAndWeddingProfileId(id, weddingId)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));
    }

    private void apply(Guest guest, GuestSaveDTO request) {
        if (request.getTableNumber() != null && request.getTableNumber() < 0) {
            throw new BadRequestException("Table number cannot be negative");
        }

        guest.setFullName(request.getFullName().trim());
        guest.setPhone(normalizeNullable(request.getPhone()));
        guest.setEmail(normalizeNullable(request.getEmail()));
        guest.setGuestSide(request.getGuestSide() != null ? request.getGuestSide() : GuestSide.BRIDE_SIDE);
        guest.setRsvpStatus(request.getRsvpStatus() != null ? request.getRsvpStatus() : RsvpStatus.PENDING);
        guest.setGroupType(normalizeNullable(request.getGroupType()));
        guest.setTableNumber(request.getTableNumber());
        guest.setNotes(normalizeNullable(request.getNotes()));
    }

    private String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}