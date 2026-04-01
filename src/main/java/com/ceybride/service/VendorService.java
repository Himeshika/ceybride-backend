package com.ceybride.service;

import com.ceybride.dto.vendor.VendorResponseDTO;
import com.ceybride.dto.vendor.VendorSaveDTO;
import com.ceybride.dto.vendor.VendorUpdateDTO;
import com.ceybride.enums.BookingStatus;
import com.ceybride.enums.VendorCategory;

import java.util.List;

public interface VendorService {
    VendorResponseDTO create(VendorSaveDTO request);
    List<VendorResponseDTO> getAll();
    List<VendorResponseDTO> filter(VendorCategory category, BookingStatus bookingStatus);
    VendorResponseDTO getById(Long id);
    VendorResponseDTO update(Long id, VendorUpdateDTO request);
    void delete(Long id);
}
