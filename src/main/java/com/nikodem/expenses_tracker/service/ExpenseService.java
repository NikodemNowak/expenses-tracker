package com.nikodem.expenses_tracker.service;

import com.nikodem.expenses_tracker.dto.ExpenseDTO;
import com.nikodem.expenses_tracker.dto.PatchExpenseDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseDTO;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    List<ExpenseDTO> findAll();
    ExpenseDTO findById(UUID expenseId);
    ExpenseDTO addExpense(PostExpenseDTO postExpenseDTO);
    ExpenseDTO updateExpense(PatchExpenseDTO patchExpenseDTO);
    void deleteExpense(UUID expenseId);
}


