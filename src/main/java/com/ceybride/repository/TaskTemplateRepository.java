package com.ceybride.repository;

import com.ceybride.entity.TaskTemplate;
import com.ceybride.enums.WeddingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long> {
    List<TaskTemplate> findByWeddingTypeAndIsActiveTrueOrderByDefaultDayOffsetAsc(WeddingType weddingType);
    long countByWeddingTypeAndIsActiveTrue(WeddingType weddingType);
    boolean existsByWeddingType(com.ceybride.enums.WeddingType weddingType);
}
