package com.nikodem.expenses_tracker.mapper;

import com.nikodem.expenses_tracker.dto.ExpenseCategoryDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseCategoryDTO;
import com.nikodem.expenses_tracker.model.ExpenseCategory;

import java.util.List;

public interface ExpenseCategoryMapper {
    ExpenseCategoryDTO toDto(ExpenseCategory expenseCategory);
    List<ExpenseCategoryDTO> toDto(List<ExpenseCategory> expenseCategories);
    ExpenseCategory toEntity(PostExpenseCategoryDTO postExpenseCategoryDTO);
}


