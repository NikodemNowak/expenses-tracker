package com.nikodem.expenses_tracker.mapper.impl;

import com.nikodem.expenses_tracker.dto.ExpenseCategoryDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseCategoryDTO;
import com.nikodem.expenses_tracker.mapper.ExpenseCategoryMapper;
import com.nikodem.expenses_tracker.model.ExpenseCategory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExpenseCategoryMapperImpl implements ExpenseCategoryMapper {
    @Override
    public ExpenseCategoryDTO toDto(ExpenseCategory expenseCategory) {
        if (expenseCategory == null) return null;
        return new ExpenseCategoryDTO(expenseCategory.getId(), expenseCategory.getName());
    }

    @Override
    public List<ExpenseCategoryDTO> toDto(List<ExpenseCategory> expenseCategories) {
        if (expenseCategories == null || expenseCategories.isEmpty()) return null;
        List<ExpenseCategoryDTO> expenseCategoryDTOs = new ArrayList<>();
        for (ExpenseCategory expenseCategory : expenseCategories) {
            expenseCategoryDTOs.add(toDto(expenseCategory));
        }
        return expenseCategoryDTOs;
    }

    @Override
    public ExpenseCategory toEntity(PostExpenseCategoryDTO postExpenseCategoryDTO) {
        return new ExpenseCategory(postExpenseCategoryDTO.getName());
    }
}
