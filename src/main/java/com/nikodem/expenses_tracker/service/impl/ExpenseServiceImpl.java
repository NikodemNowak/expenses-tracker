package com.nikodem.expenses_tracker.service.impl;

import com.nikodem.expenses_tracker.dto.ExpenseDTO;
import com.nikodem.expenses_tracker.dto.PatchExpenseDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseDTO;
import com.nikodem.expenses_tracker.exception.ExpenseInFutureException;
import com.nikodem.expenses_tracker.exception.NotFoundException;
import com.nikodem.expenses_tracker.mapper.ExpenseMapper;
import com.nikodem.expenses_tracker.model.Expense;
import com.nikodem.expenses_tracker.model.ExpenseCategory;
import com.nikodem.expenses_tracker.model.MonthlyFinance;
import com.nikodem.expenses_tracker.repository.ExpenseCategoryRepository;
import com.nikodem.expenses_tracker.repository.ExpenseRepository;
import com.nikodem.expenses_tracker.repository.MonthlyFinanceRepository;
import com.nikodem.expenses_tracker.service.ExpenseService;
import jakarta.transaction.Transactional;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final MonthlyFinanceRepository monthlyFinanceRepository;

    public ExpenseServiceImpl(
            ExpenseRepository expenseRepository,
            ExpenseMapper expenseMapper,
            ExpenseCategoryRepository expenseCategoryRepository,
            MonthlyFinanceRepository monthlyFinanceRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.monthlyFinanceRepository = monthlyFinanceRepository;
    }

    @Override
    public List<ExpenseDTO> findAll() {
        return expenseMapper.toDto(expenseRepository.findAll());
    }

    @Override
    public ExpenseDTO findById(UUID expenseId) {
        return expenseMapper.toDto(
                expenseRepository
                        .findById(expenseId)
                        .orElseThrow(() -> new NotFoundException(
                                "Expense with id " + expenseId + " not found"
                        ))
        );
    }

    @Override
    @Transactional
    public ExpenseDTO addExpense(PostExpenseDTO postExpenseDTO) {
        return expenseMapper.toDto(
                expenseRepository.save(
                        expenseMapper.toEntity(postExpenseDTO)
                )
        );
    }

    @Override
    @Transactional
    public ExpenseDTO updateExpense(PatchExpenseDTO patchExpenseDTO) {
        Expense expense = expenseRepository
                .findById(patchExpenseDTO.getExpenseId())
                .orElseThrow(() -> new NotFoundException(
                        "Expense with id " + patchExpenseDTO.getExpenseId() + " not found"
                ));
        if (patchExpenseDTO.getName() != null)
            expense.setName(patchExpenseDTO.getName());
        if (patchExpenseDTO.getDescription() != null)
            expense.setDescription(patchExpenseDTO.getDescription());
        if (patchExpenseDTO.getAmount() != null)
            expense.setAmount(patchExpenseDTO.getAmount());
        if (patchExpenseDTO.getCategoryName() != null &&
                !Objects.equals(expense.getCategory().getName(), patchExpenseDTO.getCategoryName())
        ) {
            ExpenseCategory category = expenseCategoryRepository
                    .findByName(patchExpenseDTO.getCategoryName())
                    .orElseThrow(() -> new NotFoundException(
                            "Category with name " + patchExpenseDTO.getCategoryName() + " not found"
                    ));
            expense.setCategory(category);
        }
        if (patchExpenseDTO.getExpenseDate() != null) {
            LocalDate newDate = LocalDate.parse(patchExpenseDTO.getExpenseDate());
            LocalDate date = expense.getExpenseDate();
            if (newDate.getDayOfMonth() != date.getDayOfMonth()
                    || newDate.getMonth() != date.getMonth()
                    || newDate.getYear() != date.getYear()
            ) {
                if (newDate.isAfter(LocalDate.now())) {
                    throw new ExpenseInFutureException("Expense date cannot be in the future");
                }
                MonthlyFinance newMonthlyFinance = monthlyFinanceRepository
                        .findByUserAndMonthAndYearAndExpiredIsFalse(
                                expense.getMonthlyFinance().getUser(),
                                newDate.getMonth(),
                                newDate.getYear()
                        )
                        .orElseThrow(() -> new NotFoundException(
                                "Monthly finance with criteria for user with id "
                                        + expense.getMonthlyFinance().getUser().getId() +
                                        ", month " + newDate.getMonth().toString() +
                                        " and year " + newDate.getYear() + " not found"
                        ));
                expense.getMonthlyFinance().getExpenses().remove(expense);
                expense.getMonthlyFinance().setExpensesTotal(
                        expense.getMonthlyFinance().getExpensesTotal().subtract(expense.getAmount())
                );
                expense.setMonthlyFinance(newMonthlyFinance);
                newMonthlyFinance.setExpensesTotal(
                        newMonthlyFinance.getExpensesTotal().add(expense.getAmount())
                );
                newMonthlyFinance.getExpenses().add(expense);
            }
            expense.setExpenseDate(newDate);
        }
        return expenseMapper.toDto(expenseRepository.save(expense));
    }

    @Override
    public void deleteExpense(UUID expenseId) {
        Expense expense = expenseRepository
                .findById(expenseId)
                .orElseThrow(() -> new NotFoundException(
                        "Expense with id " + expenseId + " not found"
                ));
        expenseRepository.delete(expense);
    }
}
