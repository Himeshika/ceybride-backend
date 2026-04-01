package com.ceybride.dto.expense;

import com.ceybride.enums.ExpenseCategory;
import com.ceybride.enums.PaymentStatus;
import com.ceybride.enums.ResponsibleSide;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ExpenseResponseDTO {
    private Long id;
    private String title;
    private ExpenseCategory category;
    private BigDecimal estimatedAmount;
    private BigDecimal actualAmount;
    private BigDecimal paidAmount;
    private BigDecimal remainingAmount;
    private ResponsibleSide responsibleSide;
    private PaymentStatus paymentStatus;
    private LocalDate dueDate;
    private Long vendorId;
    private String vendorName;
    private String notes;
    private boolean generated;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
