package com.ceybride.mapper;

import com.ceybride.dto.expense.ExpenseResponseDTO;
import com.ceybride.entity.Expense;

public final class ExpenseMapper {
    private ExpenseMapper() {
    }

    public static ExpenseResponseDTO toResponse(Expense expense) {
        return ExpenseResponseDTO.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .category(expense.getCategory())
                .estimatedAmount(expense.getEstimatedAmount())
                .actualAmount(expense.getActualAmount())
                .paidAmount(expense.getPaidAmount())
                .remainingAmount(expense.getRemainingAmount())
                .responsibleSide(expense.getResponsibleSide())
                .paymentStatus(expense.getPaymentStatus())
                .dueDate(expense.getDueDate())
                .vendorId(expense.getVendor() != null ? expense.getVendor().getId() : null)
                .vendorName(expense.getVendor() != null ? expense.getVendor().getName() : null)
                .notes(expense.getNotes())
                .generated(expense.isGenerated())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .build();
    }
}
