package com.ceybride.controller;

import com.ceybride.dto.common.ApiResponse;
import com.ceybride.dto.expense.*;
import com.ceybride.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseResponseDTO>> create(@Valid @RequestBody ExpenseSaveDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Expense created successfully", expenseService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExpenseResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Expenses fetched successfully", expenseService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Expense fetched successfully", expenseService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody ExpenseUpdateDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Expense updated successfully", expenseService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Expense deleted successfully", null));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<ExpenseSummaryDTO>> summary() {
        return ResponseEntity.ok(ApiResponse.success("Expense summary fetched successfully", expenseService.getSummary()));
    }

    @GetMapping("/by-side")
    public ResponseEntity<ApiResponse<List<ExpenseBySideDTO>>> bySide() {
        return ResponseEntity.ok(ApiResponse.success("Expense breakdown by side fetched successfully", expenseService.getBySide()));
    }
}
