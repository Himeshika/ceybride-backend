package com.ceybride.service;

import com.ceybride.dto.wedding.WeddingProfileCreateDTO;
import com.ceybride.dto.wedding.WeddingProfileResponseDTO;
import com.ceybride.dto.wedding.WeddingProfileUpdateDTO;
import com.ceybride.entity.WeddingProfile;

public interface WeddingProfileService {
    WeddingProfileResponseDTO create(WeddingProfileCreateDTO request);
    WeddingProfileResponseDTO getCurrentProfile();
    WeddingProfileResponseDTO update(WeddingProfileUpdateDTO request);
    WeddingProfile getCurrentWeddingProfileEntity();
}
