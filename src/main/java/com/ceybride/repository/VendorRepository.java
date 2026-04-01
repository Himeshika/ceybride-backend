package com.ceybride.repository;

import com.ceybride.entity.Vendor;
import com.ceybride.enums.BookingStatus;
import com.ceybride.enums.VendorCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    List<Vendor> findByWeddingProfileIdOrderByCreatedAtDesc(Long weddingProfileId);
    List<Vendor> findByWeddingProfileIdAndCategoryOrderByCreatedAtDesc(Long weddingProfileId, VendorCategory category);
    List<Vendor> findByWeddingProfileIdAndBookingStatusOrderByCreatedAtDesc(Long weddingProfileId, BookingStatus bookingStatus);
    Optional<Vendor> findByIdAndWeddingProfileId(Long id, Long weddingProfileId);

    List<Vendor> findByWeddingProfileIdAndCategoryAndBookingStatusOrderByCreatedAtDesc(Long weddingId, VendorCategory category, BookingStatus bookingStatus);
}
