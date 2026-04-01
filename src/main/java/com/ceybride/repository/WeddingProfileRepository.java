package com.ceybride.repository;

import com.ceybride.entity.WeddingProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeddingProfileRepository extends JpaRepository<WeddingProfile, Long> {
    Optional<WeddingProfile> findByUserId(Long userId);
}
