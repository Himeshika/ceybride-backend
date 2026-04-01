package com.ceybride.service;

import com.ceybride.dto.expense.ExpenseBySideDTO;
import com.ceybride.dto.expense.ExpenseResponseDTO;
import com.ceybride.dto.expense.ExpenseSaveDTO;
import com.ceybride.dto.expense.ExpenseSummaryDTO;
import com.ceybride.dto.expense.ExpenseUpdateDTO;

import java.util.List;

public interface ExpenseService {
    ExpenseResponseDTO create(ExpenseSaveDTO request);
    List<ExpenseResponseDTO> getAll();
    ExpenseResponseDTO getById(Long id);
    ExpenseResponseDTO update(Long id, ExpenseUpdateDTO request);
    void delete(Long id);
    ExpenseSummaryDTO getSummary();
    List<ExpenseBySideDTO> getBySide();
}
