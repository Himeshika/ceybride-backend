package com.ceybride.dto.vendor;

import com.ceybride.enums.BookingStatus;
import com.ceybride.enums.VendorCategory;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class VendorResponseDTO {
    private Long id;
    private String name;
    private VendorCategory category;
    private String phone;
    private String email;
    private String address;
    private BigDecimal estimatedCost;
    private BigDecimal actualCost;
    private BookingStatus bookingStatus;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
