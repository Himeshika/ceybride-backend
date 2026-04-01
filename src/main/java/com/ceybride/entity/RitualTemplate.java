package com.ceybride.entity;

import com.ceybride.enums.WeddingType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ritual_templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RitualTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WeddingType weddingType;

    @Column(nullable = false, length = 150)
    private String ritualName;

    @Column(nullable = false)
    private Integer defaultDayOffset;

    @Column(nullable = false)
    private Integer sequenceOrder;

    @Column(length = 50)
    private String defaultLuckyColor;

    @Column(length = 50)
    private String defaultFacingDirection;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false)
    private boolean isActive;
}
