package com.ceybride.entity;

import com.ceybride.enums.WeddingStatus;
import com.ceybride.enums.WeddingType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "wedding_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeddingProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 150)
    private String weddingTitle;

    @Column(nullable = false, length = 120)
    private String brideName;

    @Column(nullable = false, length = 120)
    private String groomName;

    @Column(length = 20)
    private String bridePhone;

    @Column(length = 20)
    private String groomPhone;

    @Column(length = 120)
    private String brideEmail;

    @Column(length = 120)
    private String groomEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WeddingType weddingType;

    @Column(nullable = false)
    private LocalDate weddingDate;

    @Column(nullable = false)
    private Integer guestCount;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal totalBudget;

    @Column(length = 180)
    private String venue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WeddingStatus status;
}
