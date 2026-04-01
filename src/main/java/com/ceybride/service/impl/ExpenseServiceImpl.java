package com.ceybride.service.impl;

import com.ceybride.dto.expense.ExpenseBySideDTO;
import com.ceybride.dto.expense.ExpenseResponseDTO;
import com.ceybride.dto.expense.ExpenseSaveDTO;
import com.ceybride.dto.expense.ExpenseSummaryDTO;
import com.ceybride.dto.expense.ExpenseUpdateDTO;
import com.ceybride.entity.Expense;
import com.ceybride.entity.Vendor;
import com.ceybride.entity.WeddingProfile;
import com.ceybride.enums.ExpenseCategory;
import com.ceybride.enums.PaymentStatus;
import com.ceybride.enums.ResponsibleSide;
import com.ceybride.exception.BadRequestException;
import com.ceybride.exception.ResourceNotFoundException;
import com.ceybride.mapper.ExpenseMapper;
import com.ceybride.repository.ExpenseRepository;
import com.ceybride.repository.VendorRepository;
import com.ceybride.service.ExpenseService;
import com.ceybride.service.WeddingProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final VendorRepository vendorRepository;
    private final WeddingProfileService weddingProfileService;

    @Override
    @Transactional
    public ExpenseResponseDTO create(ExpenseSaveDTO request) {
        WeddingProfile profile = weddingProfileService.getCurrentWeddingProfileEntity();

        Expense expense = Expense.builder()
                .weddingProfile(profile)
                .isGenerated(false)
                .build();

        apply(expense, request, profile.getId());
        expenseRepository.save(expense);

        return ExpenseMapper.toResponse(expense);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDTO> getAll() {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();
        return expenseRepository.findByWeddingProfileIdOrderByCreatedAtDesc(weddingId)
                .stream()
                .map(ExpenseMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseResponseDTO getById(Long id) {
        return ExpenseMapper.toResponse(getEntity(id));
    }

    @Override
    @Transactional
    public ExpenseResponseDTO update(Long id, ExpenseUpdateDTO request) {
        Expense expense = getEntity(id);
        apply(expense, request, expense.getWeddingProfile().getId());
        expenseRepository.save(expense);
        return ExpenseMapper.toResponse(expense);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        expenseRepository.delete(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseSummaryDTO getSummary() {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();
        return ExpenseSummaryDTO.builder()
                .estimatedTotal(nz(expenseRepository.sumEstimatedAmountByWeddingProfileId(weddingId)))
                .actualTotal(nz(expenseRepository.sumActualAmountByWeddingProfileId(weddingId)))
                .paidTotal(nz(expenseRepository.sumPaidAmountByWeddingProfileId(weddingId)))
                .remainingTotal(nz(expenseRepository.sumRemainingAmountByWeddingProfileId(weddingId)))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseBySideDTO> getBySide() {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();

        return Arrays.stream(ResponsibleSide.values())
                .map(side -> ExpenseBySideDTO.builder()
                        .responsibleSide(side)
                        .totalAmount(nz(expenseRepository.sumEffectiveAmountByWeddingProfileIdAndResponsibleSide(weddingId, side)))
                        .build())
                .toList();
    }

    private Expense getEntity(Long id) {
        Long weddingId = weddingProfileService.getCurrentWeddingProfileEntity().getId();
        return expenseRepository.findByIdAndWeddingProfileId(id, weddingId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
    }

    private void apply(Expense expense, ExpenseSaveDTO request, Long weddingProfileId) {
        BigDecimal estimated = defaultZero(request.getEstimatedAmount());
        BigDecimal actual = request.getActualAmount();
        BigDecimal paid = defaultZero(request.getPaidAmount());

        if (actual != null && actual.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Actual amount cannot be negative");
        }
        if (paid.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Paid amount cannot be negative");
        }

        BigDecimal baseAmount = actual != null ? actual : estimated;
        if (paid.compareTo(baseAmount) > 0) {
            throw new BadRequestException("Paid amount cannot exceed actual/estimated amount");
        }

        expense.setTitle(request.getTitle().trim());
        expense.setCategory(request.getCategory() != null ? request.getCategory() : ExpenseCategory.OTHER);
        expense.setEstimatedAmount(estimated);
        expense.setActualAmount(actual);
        expense.setPaidAmount(paid);
        expense.setRemainingAmount(baseAmount.subtract(paid));
        expense.setResponsibleSide(request.getResponsibleSide() != null ? request.getResponsibleSide() : ResponsibleSide.SHARED);
        expense.setPaymentStatus(resolvePaymentStatus(baseAmount, paid));
        expense.setDueDate(request.getDueDate());
        expense.setVendor(resolveVendor(request.getVendorId(), weddingProfileId));
        expense.setNotes(normalizeNullable(request.getNotes()));
    }

    private PaymentStatus resolvePaymentStatus(BigDecimal baseAmount, BigDecimal paid) {
        if (paid.compareTo(BigDecimal.ZERO) == 0) {
            return PaymentStatus.PENDING;
        }
        if (paid.compareTo(baseAmount) >= 0) {
            return PaymentStatus.PAID;
        }
        return PaymentStatus.PARTIAL;
    }

    private Vendor resolveVendor(Long vendorId, Long weddingProfileId) {
        if (vendorId == null) {
            return null;
        }

        return vendorRepository.findByIdAndWeddingProfileId(vendorId, weddingProfileId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found for expense"));
    }

    private BigDecimal defaultZero(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    private BigDecimal nz(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    private String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}