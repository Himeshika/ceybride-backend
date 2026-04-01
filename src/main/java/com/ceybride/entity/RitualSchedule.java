package com.ceybride.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ritual_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RitualSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wedding_profile_id", nullable = false)
    private WeddingProfile weddingProfile;

    @Column(nullable = false, length = 150)
    private String ritualName;

    private LocalDateTime eventDateTime;

    @Column(length = 50)
    private String luckyColor;

    @Column(length = 50)
    private String facingDirection;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false)
    private Integer sequenceOrder;

    @Column(nullable = false)
    private boolean isCompleted;

    @Column(nullable = false)
    private boolean isGenerated;
}
