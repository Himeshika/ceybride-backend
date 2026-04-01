package com.ceybride.service;

import com.ceybride.dto.guest.GuestResponseDTO;
import com.ceybride.dto.guest.GuestSaveDTO;
import com.ceybride.dto.guest.GuestStatsDTO;
import com.ceybride.dto.guest.GuestUpdateDTO;

import java.util.List;

public interface GuestService {
    GuestResponseDTO create(GuestSaveDTO request);
    List<GuestResponseDTO> getAll();
    GuestResponseDTO getById(Long id);
    GuestResponseDTO update(Long id, GuestUpdateDTO request);
    void delete(Long id);
    GuestStatsDTO getStats();
}
