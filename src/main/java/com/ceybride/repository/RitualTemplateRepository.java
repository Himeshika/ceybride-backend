package com.ceybride.repository;

import com.ceybride.entity.RitualTemplate;
import com.ceybride.enums.WeddingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RitualTemplateRepository extends JpaRepository<RitualTemplate, Long> {
    List<RitualTemplate> findByWeddingTypeAndIsActiveTrueOrderBySequenceOrderAsc(WeddingType weddingType);
    long countByWeddingTypeAndIsActiveTrue(WeddingType weddingType);
    boolean existsByWeddingType(com.ceybride.enums.WeddingType weddingType);
}
