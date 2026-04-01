package com.ceybride.entity;

import com.ceybride.enums.ExpenseCategory;
import com.ceybride.enums.ResponsibleSide;
import com.ceybride.enums.WeddingType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "expense_templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WeddingType weddingType;

    @Column(nullable = false, length = 150)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ExpenseCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ResponsibleSide responsibleSide;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal defaultBudgetPercentage;

    @Column(nullable = false)
    private boolean isActive;
}
