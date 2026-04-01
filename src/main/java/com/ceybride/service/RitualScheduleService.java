package com.ceybride.service;

import com.ceybride.dto.ritual.RitualResponseDTO;
import com.ceybride.dto.ritual.RitualSaveDTO;
import com.ceybride.dto.ritual.RitualUpdateDTO;

import java.util.List;

public interface RitualScheduleService {
    RitualResponseDTO create(RitualSaveDTO request);
    List<RitualResponseDTO> getAll();
    RitualResponseDTO getById(Long id);
    RitualResponseDTO update(Long id, RitualUpdateDTO request);
    void delete(Long id);
    List<RitualResponseDTO> getUpcoming();
}
