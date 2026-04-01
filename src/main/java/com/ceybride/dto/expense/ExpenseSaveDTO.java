package com.ceybride.dto.expense;

import com.ceybride.enums.ExpenseCategory;
import com.ceybride.enums.PaymentStatus;
import com.ceybride.enums.ResponsibleSide;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseSaveDTO {

    @NotBlank
    @Size(max = 150)
    private String title;

    private ExpenseCategory category;

    @DecimalMin(value = "0.00")
    private BigDecimal estimatedAmount;

    @DecimalMin(value = "0.00")
    private BigDecimal actualAmount;

    @DecimalMin(value = "0.00")
    private BigDecimal paidAmount;

    private ResponsibleSide responsibleSide;
    private PaymentStatus paymentStatus;
    private LocalDate dueDate;
    private Long vendorId;

    @Size(max = 500)
    private String notes;
}
