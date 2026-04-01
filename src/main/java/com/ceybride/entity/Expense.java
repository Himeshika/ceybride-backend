package com.ceybride.entity;

import com.ceybride.enums.ExpenseCategory;
import com.ceybride.enums.PaymentStatus;
import com.ceybride.enums.ResponsibleSide;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wedding_profile_id", nullable = false)
    private WeddingProfile weddingProfile;

    @Column(nullable = false, length = 150)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ExpenseCategory category;

    @Column(precision = 15, scale = 2)
    private BigDecimal estimatedAmount;

    @Column(precision = 15, scale = 2)
    private BigDecimal actualAmount;

    @Column(precision = 15, scale = 2)
    private BigDecimal paidAmount;

    @Column(precision = 15, scale = 2)
    private BigDecimal remainingAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ResponsibleSide responsibleSide;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false)
    private boolean isGenerated;
}
