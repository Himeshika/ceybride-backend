package com.ceybride.repository;

import com.ceybride.entity.ExpenseTemplate;
import com.ceybride.enums.WeddingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseTemplateRepository extends JpaRepository<ExpenseTemplate, Long> {
    List<ExpenseTemplate> findByWeddingTypeAndIsActiveTrue(WeddingType weddingType);
    long countByWeddingTypeAndIsActiveTrue(WeddingType weddingType);
    boolean existsByWeddingType(com.ceybride.enums.WeddingType weddingType);
}
