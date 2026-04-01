package com.ceybride.service.impl;

import com.ceybride.dto.ritual.RitualResponseDTO;
import com.ceybride.dto.ritual.RitualSaveDTO;
import com.ceybride.dto.ritual.RitualUpdateDTO;
import com.ceybride.entity.RitualSchedule;
import com.ceybride.entity.WeddingProfile;
import com.ceybride.exception.BadRequestException;
import com.ceybride.exception.ResourceNotFoundException;
import com.ceybride.mapper.RitualScheduleMapper;
import com.ceybride.repository.RitualScheduleRepository;
import com.ceybride.service.RitualScheduleService;
import com.ceybride.service.WeddingProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RitualScheduleServiceImpl implements RitualScheduleService {

    private final RitualScheduleRepository ritualScheduleRepository;
    private final WeddingProfileService weddingProfileService;

    @Override
    @Transactional
    public RitualResponseDTO create(RitualSaveDTO request) {
        WeddingProfile profile = weddingProfileService.getCurrentWeddingProfileEntity();

        RitualSchedule ritual = RitualSchedule.builder()
                .weddingProfile(profile)
                .isGenerated(false)
                .build();

        apply(ritual, request);
        ritualScheduleRepository.save(ritual);

        return RitualScheduleMapper.toResponse(ritual);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RitualResponseDTO> getAll() {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();
        return ritualScheduleRepository.findByWeddingProfileIdOrderBySequenceOrderAsc(weddingId)
                .stream()
                .map(RitualScheduleMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RitualResponseDTO getById(Long id) {
        return RitualScheduleMapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public RitualResponseDTO update(Long id, RitualUpdateDTO request) {
        RitualSchedule ritual = getEntity(id);
        apply(ritual, request);
        ritualScheduleRepository.save(ritual);
        return RitualScheduleMapper.toResponse(ritual);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ritualScheduleRepository.delete(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RitualResponseDTO> getUpcoming() {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();
        return ritualScheduleRepository
                .findTop5ByWeddingProfileIdAndEventDateTimeGreaterThanEqualOrderByEventDateTimeAsc(
                        weddingId,
                        LocalDateTime.now()
                )
                .stream()
                .map(RitualScheduleMapper::toResponse)
                .toList();
    }

    private RitualSchedule getEntity(Long id) {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();
        return ritualScheduleRepository.findByIdAndWeddingProfileId(id, weddingId)
                .orElseThrow(() -> new ResourceNotFoundException("Ritual not found"));
    }

    private void apply(RitualSchedule ritual, RitualSaveDTO request) {
        Integer sequenceOrder = request.getSequenceOrder() != null ? request.getSequenceOrder() : 1;
        if (sequenceOrder <= 0) {
            throw new BadRequestException("Sequence order must be greater than zero");
        }

        ritual.setRitualName(request.getRitualName().trim());
        ritual.setEventDateTime(request.getEventDateTime());
        ritual.setLuckyColor(normalizeNullable(request.getLuckyColor()));
        ritual.setFacingDirection(normalizeNullable(request.getFacingDirection()));
        ritual.setNotes(normalizeNullable(request.getNotes()));
        ritual.setSequenceOrder(sequenceOrder);
        ritual.setCompleted(request.getCompleted() != null ? request.getCompleted() : false);
    }

    private String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}