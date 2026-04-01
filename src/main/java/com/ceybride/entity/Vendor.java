package com.ceybride.entity;

import com.ceybride.enums.BookingStatus;
import com.ceybride.enums.VendorCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "vendors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vendor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wedding_profile_id", nullable = false)
    private WeddingProfile weddingProfile;

    @Column(nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private VendorCategory category;

    @Column(length = 20)
    private String phone;

    @Column(length = 120)
    private String email;

    @Column(length = 255)
    private String address;

    @Column(precision = 15, scale = 2)
    private BigDecimal estimatedCost;

    @Column(precision = 15, scale = 2)
    private BigDecimal actualCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookingStatus bookingStatus;

    @Column(length = 500)
    private String notes;
}
