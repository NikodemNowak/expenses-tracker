package com.nikodem.expenses_tracker.mapper;

import com.nikodem.expenses_tracker.dto.ExpenseDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseDTO;
import com.nikodem.expenses_tracker.model.*;

import java.util.List;

public interface ExpenseMapper {
    ExpenseDTO toDto(Expense expense);
    List<ExpenseDTO> toDto(List<Expense> expenses);
    Expense toEntity(PostExpenseDTO postExpenseDTO);
}


