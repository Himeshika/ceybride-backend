package com.ceybride.repository;

import com.ceybride.entity.Task;
import com.ceybride.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByWeddingProfileIdOrderByDueDateAscCreatedAtDesc(Long weddingProfileId);
    Optional<Task> findByIdAndWeddingProfileId(Long id, Long weddingProfileId);
    long countByWeddingProfileId(Long weddingProfileId);
    long countByWeddingProfileIdAndStatus(Long weddingProfileId, TaskStatus status);
    List<Task> findTop5ByWeddingProfileIdAndDueDateGreaterThanEqualOrderByDueDateAsc(Long weddingProfileId, LocalDate date);
    void deleteByWeddingProfileIdAndIsGeneratedTrue(Long weddingProfileId);

    boolean existsByWeddingProfileIdAndIsGeneratedTrue(Long id);
}
