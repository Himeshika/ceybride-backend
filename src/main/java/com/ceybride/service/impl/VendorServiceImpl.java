package com.ceybride.service.impl;

import com.ceybride.dto.vendor.VendorResponseDTO;
import com.ceybride.dto.vendor.VendorSaveDTO;
import com.ceybride.dto.vendor.VendorUpdateDTO;
import com.ceybride.entity.Vendor;
import com.ceybride.entity.WeddingProfile;
import com.ceybride.enums.BookingStatus;
import com.ceybride.enums.VendorCategory;
import com.ceybride.exception.ResourceNotFoundException;
import com.ceybride.mapper.VendorMapper;
import com.ceybride.repository.VendorRepository;
import com.ceybride.service.VendorService;
import com.ceybride.service.WeddingProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final WeddingProfileService weddingProfileService;

    @Override
    @Transactional
    public VendorResponseDTO create(VendorSaveDTO request) {
        WeddingProfile profile = weddingProfileService.getCurrentWeddingProfileEntity();

        Vendor vendor = Vendor.builder()
                .weddingProfile(profile)
                .build();

        apply(vendor, request);
        vendorRepository.save(vendor);

        return VendorMapper.toResponse(vendor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VendorResponseDTO> getAll() {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();
        return vendorRepository.findByWeddingProfileIdOrderByCreatedAtDesc(weddingId)
                .stream()
                .map(VendorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VendorResponseDTO> filter(VendorCategory category, BookingStatus bookingStatus) {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();

        List<Vendor> vendors;
        if (category != null && bookingStatus != null) {
            vendors = vendorRepository.findByWeddingProfileIdAndCategoryAndBookingStatusOrderByCreatedAtDesc(
                    weddingId, category, bookingStatus
            );
        } else if (category != null) {
            vendors = vendorRepository.findByWeddingProfileIdAndCategoryOrderByCreatedAtDesc(weddingId, category);
        } else if (bookingStatus != null) {
            vendors = vendorRepository.findByWeddingProfileIdAndBookingStatusOrderByCreatedAtDesc(weddingId, bookingStatus);
        } else {
            vendors = vendorRepository.findByWeddingProfileIdOrderByCreatedAtDesc(weddingId);
        }

        return vendors.stream()
                .map(VendorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VendorResponseDTO getById(Long id) {
        return VendorMapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public VendorResponseDTO update(Long id, VendorUpdateDTO request) {
        Vendor vendor = getEntity(id);
        apply(vendor, request);
        vendorRepository.save(vendor);
        return VendorMapper.toResponse(vendor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        vendorRepository.delete(getEntity(id));
    }

    private Vendor getEntity(Long id) {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();
        return vendorRepository.findByIdAndWeddingProfileId(id, weddingId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
    }

    private void apply(Vendor vendor, VendorSaveDTO request) {
        vendor.setName(request.getName().trim());
        vendor.setCategory(request.getCategory() != null ? request.getCategory() : VendorCategory.OTHER);
        vendor.setPhone(normalizeNullable(request.getPhone()));
        vendor.setEmail(normalizeNullable(request.getEmail()));
        vendor.setAddress(normalizeNullable(request.getAddress()));
        vendor.setEstimatedCost(request.getEstimatedCost());
        vendor.setActualCost(request.getActualCost());
        vendor.setBookingStatus(request.getBookingStatus() != null ? request.getBookingStatus() : BookingStatus.NOT_BOOKED);
        vendor.setNotes(normalizeNullable(request.getNotes()));
    }

    private String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}