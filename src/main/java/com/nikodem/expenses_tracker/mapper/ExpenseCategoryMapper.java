package com.nikodem.expenses_tracker.mapper;

import com.nikodem.expenses_tracker.dto.ExpenseCategoryDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseCategoryDTO;
import com.nikodem.expenses_tracker.model.ExpenseCategory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

interface IExpenseCategoryMapper {
    ExpenseCategoryDTO toDto(ExpenseCategory expenseCategory);
    List<ExpenseCategoryDTO> toDto(List<ExpenseCategory> expenseCategories);
    ExpenseCategory toEntity(PostExpenseCategoryDTO postExpenseCategoryDTO);
}

@Component
public class ExpenseCategoryMapper implements IExpenseCategoryMapper{
    @Override
    public ExpenseCategoryDTO toDto(ExpenseCategory expenseCategory) {
        return new ExpenseCategoryDTO(expenseCategory.getId(), expenseCategory.getName());
    }

    @Override
    public List<ExpenseCategoryDTO> toDto(List<ExpenseCategory> expenseCategories) {
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
