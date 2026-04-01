package com.ceybride.repository;

import com.ceybride.entity.RitualSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RitualScheduleRepository extends JpaRepository<RitualSchedule, Long> {
    List<RitualSchedule> findByWeddingProfileIdOrderBySequenceOrderAsc(Long weddingProfileId);
    Optional<RitualSchedule> findByIdAndWeddingProfileId(Long id, Long weddingProfileId);
    List<RitualSchedule> findTop5ByWeddingProfileIdAndEventDateTimeGreaterThanEqualOrderByEventDateTimeAsc(Long weddingProfileId, LocalDateTime now);
    void deleteByWeddingProfileIdAndIsGeneratedTrue(Long weddingProfileId);

    boolean existsByWeddingProfileIdAndIsGeneratedTrue(Long id);
}
