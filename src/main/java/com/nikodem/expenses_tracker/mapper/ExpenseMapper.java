package com.nikodem.expenses_tracker.mapper;

import com.nikodem.expenses_tracker.dto.ExpenseDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseDTO;
import com.nikodem.expenses_tracker.exception.EmptyArgumentException;
import com.nikodem.expenses_tracker.exception.NotFoundException;
import com.nikodem.expenses_tracker.model.Expense;
import com.nikodem.expenses_tracker.model.ExpenseCategory;
import com.nikodem.expenses_tracker.model.MonthlyFinance;
import com.nikodem.expenses_tracker.repository.ExpenseCategoryRepository;
import com.nikodem.expenses_tracker.repository.MonthlyFinanceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

interface IExpenseMapper {
    ExpenseDTO toDto(Expense expense);

    List<ExpenseDTO> toDto(List<Expense> expenses);

    Expense toEntity(PostExpenseDTO postExpenseDTO);
}

@Component
public class ExpenseMapper implements IExpenseMapper {

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final MonthlyFinanceRepository monthlyFinanceRepository;

    public ExpenseMapper(
            ExpenseCategoryRepository expenseCategoryRepository,
            MonthlyFinanceRepository monthlyFinanceRepository
    ) {
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.monthlyFinanceRepository = monthlyFinanceRepository;
    }

    @Override
    public ExpenseDTO toDto(Expense expense) {
        if (expense == null) return null;
        return new ExpenseDTO(
                expense.getId(),
                expense.getName(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getExpenseDate(),
                expense.getMonthlyFinance()
        );
    }

    @Override
    public List<ExpenseDTO> toDto(List<Expense> expenses) {
        if (expenses == null) return null;
        List<ExpenseDTO> expenseDTOs = new ArrayList<>();
        for (Expense expense : expenses) {
            expenseDTOs.add(toDto(expense));
        }
        return expenseDTOs;
    }

    @Override
    public Expense toEntity(PostExpenseDTO postExpenseDTO) {
        if (postExpenseDTO == null) {
            throw new EmptyArgumentException("Given expense is null");
        }
        ExpenseCategory category = expenseCategoryRepository
                .findByName(postExpenseDTO.getCategoryName())
                .orElseThrow(() -> new NotFoundException(
                        "Expense category with name " + postExpenseDTO.getCategoryName() + " not found"
                ));
        LocalDate expenseDate = LocalDate.parse(postExpenseDTO.getExpenseDate());
        MonthlyFinance monthlyFinance =
                monthlyFinanceRepository
                        .findById(UUID.fromString(postExpenseDTO.getMonthlyFinanceId()))
                        .orElseThrow(() -> new NotFoundException(
                                "Monthly finance with id " + postExpenseDTO.getMonthlyFinanceId() + " not found"
                        ));
        return new Expense(
                postExpenseDTO.getName(),
                postExpenseDTO.getDescription(),
                postExpenseDTO.getAmount(),
                category,
                expenseDate,
                monthlyFinance
                );
    }
}
