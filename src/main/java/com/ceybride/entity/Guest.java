package com.ceybride.entity;

import com.ceybride.enums.GuestSide;
import com.ceybride.enums.RsvpStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "guests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wedding_profile_id", nullable = false)
    private WeddingProfile weddingProfile;

    @Column(nullable = false, length = 120)
    private String fullName;

    @Column(length = 20)
    private String phone;

    @Column(length = 120)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GuestSide guestSide;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RsvpStatus rsvpStatus;

    @Column(length = 80)
    private String groupType;

    private Integer tableNumber;

    @Column(length = 500)
    private String notes;
}
