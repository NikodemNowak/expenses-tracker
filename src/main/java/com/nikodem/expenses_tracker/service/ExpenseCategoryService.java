package com.nikodem.expenses_tracker.service;

import com.nikodem.expenses_tracker.dto.ExpenseCategoryDTO;
import com.nikodem.expenses_tracker.dto.PatchExpenseCategoryDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseCategoryDTO;

import java.util.List;

public interface ExpenseCategoryService {
    List<ExpenseCategoryDTO> findAll();
    ExpenseCategoryDTO findByName(String name);
    ExpenseCategoryDTO addExpenseCategory(PostExpenseCategoryDTO postExpenseCategoryDTO);
    ExpenseCategoryDTO updateExpenseCategory(PatchExpenseCategoryDTO patchExpenseCategoryDTO);
    void deleteExpenseCategory(String name);
}


