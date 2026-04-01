package com.ceybride.service;

import com.ceybride.dto.wedding.BlueprintGenerateRequestDTO;
import com.ceybride.dto.wedding.BlueprintGenerateResponseDTO;
import com.ceybride.dto.wedding.BlueprintPreviewDTO;

public interface BlueprintGeneratorService {
    BlueprintGenerateResponseDTO generate(BlueprintGenerateRequestDTO request);
    BlueprintGenerateResponseDTO regenerate(BlueprintGenerateRequestDTO request);
    BlueprintPreviewDTO preview();
}
