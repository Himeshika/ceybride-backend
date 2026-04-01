package com.ceybride.repository;

import com.ceybride.entity.Guest;
import com.ceybride.enums.GuestSide;
import com.ceybride.enums.RsvpStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findByWeddingProfileIdOrderByCreatedAtDesc(Long weddingProfileId);
    Optional<Guest> findByIdAndWeddingProfileId(Long id, Long weddingProfileId);
    long countByWeddingProfileId(Long weddingProfileId);
    long countByWeddingProfileIdAndGuestSide(Long weddingProfileId, GuestSide guestSide);
    long countByWeddingProfileIdAndRsvpStatus(Long weddingProfileId, RsvpStatus rsvpStatus);
}
