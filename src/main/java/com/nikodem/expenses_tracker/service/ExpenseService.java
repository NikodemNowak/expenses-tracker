package com.nikodem.expenses_tracker.service;

import com.nikodem.expenses_tracker.dto.ExpenseDTO;
import com.nikodem.expenses_tracker.dto.PatchExpenseDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseDTO;
import com.nikodem.expenses_tracker.exception.NotFoundException;
import com.nikodem.expenses_tracker.mapper.ExpenseMapper;
import com.nikodem.expenses_tracker.model.Expense;
import com.nikodem.expenses_tracker.model.ExpenseCategory;
import com.nikodem.expenses_tracker.model.MonthlyFinance;
import com.nikodem.expenses_tracker.repository.ExpenseCategoryRepository;
import com.nikodem.expenses_tracker.repository.ExpenseRepository;
import com.nikodem.expenses_tracker.repository.MonthlyFinanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

interface IExpenseService {
    List<ExpenseDTO> findAll();
    ExpenseDTO findById(UUID expenseId);
    ExpenseDTO addExpense(PostExpenseDTO postExpenseDTO);
    ExpenseDTO updateExpense(PatchExpenseDTO patchExpenseDTO);
    void deleteExpense(UUID expenseId);
}

@Service
public class ExpenseService implements IExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final MonthlyFinanceRepository monthlyFinanceRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper, ExpenseCategoryRepository expenseCategoryRepository, MonthlyFinanceRepository monthlyFinanceRepository) {
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
    public ExpenseDTO addExpense(PostExpenseDTO postExpenseDTO) {
        return expenseMapper.toDto(
                expenseRepository.save(
                        expenseMapper.toEntity(postExpenseDTO)
                )
        );
    }

    @Override
    public ExpenseDTO updateExpense(PatchExpenseDTO patchExpenseDTO) {
        Expense expense = expenseRepository
                .findById(patchExpenseDTO.getExpenseId())
                .orElseThrow(() -> new NotFoundException(
                        "Expense with id " + patchExpenseDTO.getExpenseId() + " not found"
                ));
        if (patchExpenseDTO.getName() != null) expense.setName(patchExpenseDTO.getName());
        if (patchExpenseDTO.getDescription() != null) expense.setDescription(patchExpenseDTO.getDescription());
        if (patchExpenseDTO.getAmount() != null) expense.setAmount(patchExpenseDTO.getAmount());
        if (patchExpenseDTO.getCategoryName() != null && !Objects.equals(expense.getCategory().getName(), patchExpenseDTO.getCategoryName())) {
            ExpenseCategory category = expenseCategoryRepository.findByName(patchExpenseDTO.getCategoryName()).orElseThrow(() -> new NotFoundException("Category with name " + patchExpenseDTO.getCategoryName() + " not found"));
            expense.setCategory(category);
        }
        if (patchExpenseDTO.getExpenseDate() != null) {
            expense.setExpenseDate(LocalDate.parse(patchExpenseDTO.getExpenseDate()));
        }
        if (patchExpenseDTO.getMonthlyFinanceId() != null) {
            MonthlyFinance monthlyFinance = monthlyFinanceRepository.findById(UUID.fromString(patchExpenseDTO.getMonthlyFinanceId())).orElseThrow(() -> new NotFoundException("Monthly finance with id " + patchExpenseDTO.getMonthlyFinanceId() + " not found"));
            expense.setMonthlyFinance(monthlyFinance);
        }
        return expenseMapper.toDto(expenseRepository.save(expense));
    }

    @Override
    public void deleteExpense(UUID expenseId) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new NotFoundException("Expense with id " + expenseId + " not found"));
        expenseRepository.delete(expense);
    }
}
