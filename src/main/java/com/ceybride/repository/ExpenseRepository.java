package com.ceybride.repository;

import com.ceybride.entity.Expense;
import com.ceybride.enums.ResponsibleSide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByWeddingProfileIdOrderByCreatedAtDesc(Long weddingProfileId);
    Optional<Expense> findByIdAndWeddingProfileId(Long id, Long weddingProfileId);
    void deleteByWeddingProfileIdAndIsGeneratedTrue(Long weddingProfileId);

    @Query("select coalesce(sum(e.estimatedAmount), 0) from Expense e where e.weddingProfile.id = :weddingProfileId")
    BigDecimal sumEstimatedAmountByWeddingProfileId(Long weddingProfileId);

    @Query("select coalesce(sum(e.actualAmount), 0) from Expense e where e.weddingProfile.id = :weddingProfileId")
    BigDecimal sumActualAmountByWeddingProfileId(Long weddingProfileId);

    @Query("select coalesce(sum(e.paidAmount), 0) from Expense e where e.weddingProfile.id = :weddingProfileId")
    BigDecimal sumPaidAmountByWeddingProfileId(Long weddingProfileId);

    @Query("select coalesce(sum(e.remainingAmount), 0) from Expense e where e.weddingProfile.id = :weddingProfileId")
    BigDecimal sumRemainingAmountByWeddingProfileId(Long weddingProfileId);

    @Query("select coalesce(sum(e.actualAmount), 0) from Expense e where e.weddingProfile.id = :weddingProfileId and e.responsibleSide = :side")
    BigDecimal sumActualAmountByWeddingProfileIdAndResponsibleSide(Long weddingProfileId, ResponsibleSide side);

    @Query("select coalesce(sum(case when e.actualAmount is not null then e.actualAmount else e.estimatedAmount end), 0) from Expense e where e.weddingProfile.id = :weddingProfileId and e.responsibleSide = :side")
    BigDecimal sumEffectiveAmountByWeddingProfileIdAndResponsibleSide(Long weddingProfileId, ResponsibleSide side);

    boolean existsByWeddingProfileIdAndIsGeneratedTrue(Long id);
}
