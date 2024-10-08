package com.nikodem.expenses_tracker.mapper.impl;

import com.nikodem.expenses_tracker.dto.ExpenseDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseDTO;
import com.nikodem.expenses_tracker.exception.ExpenseInFutureException;
import com.nikodem.expenses_tracker.exception.NotFoundException;
import com.nikodem.expenses_tracker.mapper.ExpenseMapper;
import com.nikodem.expenses_tracker.model.Expense;
import com.nikodem.expenses_tracker.model.ExpenseCategory;
import com.nikodem.expenses_tracker.model.MonthlyFinance;
import com.nikodem.expenses_tracker.model.User;
import com.nikodem.expenses_tracker.repository.ExpenseCategoryRepository;
import com.nikodem.expenses_tracker.repository.MonthlyFinanceRepository;
import com.nikodem.expenses_tracker.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ExpenseMapperImpl implements ExpenseMapper {

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final MonthlyFinanceRepository monthlyFinanceRepository;
    private final UserRepository userRepository;

    public ExpenseMapperImpl(
            ExpenseCategoryRepository expenseCategoryRepository,
            MonthlyFinanceRepository monthlyFinanceRepository,
            UserRepository userRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.monthlyFinanceRepository = monthlyFinanceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ExpenseDTO toDto(Expense expense) {
        if (expense == null) return null;
        return new ExpenseDTO(
                expense.getId(),
                expense.getName(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getCategory().getName(),
                expense.getExpenseDate().toString(),
                expense.getMonthlyFinance().getId()
        );
    }

    @Override
    public List<ExpenseDTO> toDto(List<Expense> expenses) {
        if (expenses == null || expenses.isEmpty()) return null;
        List<ExpenseDTO> expenseDTOs = new ArrayList<>();
        for (Expense expense : expenses) {
            expenseDTOs.add(toDto(expense));
        }
        return expenseDTOs;
    }

    @Override
    public Expense toEntity(PostExpenseDTO postExpenseDTO) {
        ExpenseCategory category = expenseCategoryRepository
                .findByName(postExpenseDTO.getCategoryName())
                .orElseThrow(() -> new NotFoundException(
                        "Expense category with name " + postExpenseDTO.getCategoryName() + " not found"
                ));
        LocalDate expenseDate = LocalDate.parse(postExpenseDTO.getExpenseDate());
        if (expenseDate.isAfter(LocalDate.now())) {
            throw new ExpenseInFutureException("Expense date cannot be in the future");
        }
        User user = userRepository
                .findByIdAndExpiredIsFalse(UUID.fromString(postExpenseDTO.getUserId()))
                .orElseThrow(() -> new NotFoundException(
                        "User with id " + postExpenseDTO.getUserId() + " not found"
                ));
        Month month = expenseDate.getMonth();
        int year = expenseDate.getYear();
        MonthlyFinance monthlyFinance =
                monthlyFinanceRepository
                        .findByUserAndMonthAndYearAndExpiredIsFalse(user, month, year)
                        .orElseThrow(() -> new NotFoundException(
                                "Monthly finance with criteria for user with id " + user.getId() +
                                        ", month " + month.toString() +
                                        " and year " + year + " not found"
                        ));
        Expense newExpense = new Expense(
                postExpenseDTO.getName(),
                postExpenseDTO.getDescription(),
                postExpenseDTO.getAmount(),
                category,
                expenseDate,
                monthlyFinance
        );
        monthlyFinance.getExpenses().add(newExpense);
        monthlyFinance.setExpensesTotal(monthlyFinance.getExpensesTotal().add(newExpense.getAmount()));
        return newExpense;
    }
}
