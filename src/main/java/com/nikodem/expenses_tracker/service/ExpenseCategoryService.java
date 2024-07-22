package com.nikodem.expenses_tracker.service;

import com.nikodem.expenses_tracker.dto.ExpenseCategoryDTO;
import com.nikodem.expenses_tracker.dto.PatchExpenseCategoryDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseCategoryDTO;
import com.nikodem.expenses_tracker.exception.NotFoundException;
import com.nikodem.expenses_tracker.mapper.ExpenseCategoryMapper;
import com.nikodem.expenses_tracker.model.ExpenseCategory;
import com.nikodem.expenses_tracker.repository.ExpenseCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

interface IExpenseCategoryService {
    List<ExpenseCategoryDTO> findAll();
    ExpenseCategoryDTO findByName(String name);
    ExpenseCategoryDTO addExpenseCategory(PostExpenseCategoryDTO postExpenseCategoryDTO);
    ExpenseCategoryDTO updateExpenseCategory(PatchExpenseCategoryDTO patchExpenseCategoryDTO);
    void deleteExpenseCategory(String name);
}

@Service
public class ExpenseCategoryService implements IExpenseCategoryService{

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpenseCategoryMapper expenseCategoryMapper;

    @Autowired
    public ExpenseCategoryService(ExpenseCategoryRepository expenseCategoryRepository, ExpenseCategoryMapper expenseCategoryMapper) {
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.expenseCategoryMapper = expenseCategoryMapper;
    }

    @Override
    public List<ExpenseCategoryDTO> findAll() {
        return expenseCategoryMapper.toDto(expenseCategoryRepository.findAll());
    }

    @Override
    public ExpenseCategoryDTO findByName(String name) {
        return expenseCategoryMapper.toDto(expenseCategoryRepository.findByName(name).orElseThrow(() -> new NotFoundException("Expense category with name " + name + " not found")));
    }

    @Override
    public ExpenseCategoryDTO addExpenseCategory(PostExpenseCategoryDTO postExpenseCategoryDTO) {
        if (postExpenseCategoryDTO == null) throw new RuntimeException("New object of expense category cannot be null");
        if (expenseCategoryRepository.findByName(postExpenseCategoryDTO.getName()).isPresent()) throw new RuntimeException("Expense category with name " + postExpenseCategoryDTO.getName() + " already exists");
        return expenseCategoryMapper.toDto(expenseCategoryRepository.save(expenseCategoryMapper.toEntity(postExpenseCategoryDTO)));
    }

    @Override
    public ExpenseCategoryDTO updateExpenseCategory(PatchExpenseCategoryDTO patchExpenseCategoryDTO) {
        ExpenseCategory expenseCategory = expenseCategoryRepository.findById(patchExpenseCategoryDTO.getExpenseCategoryId()).orElseThrow(() -> new NotFoundException("Expense category with id " + patchExpenseCategoryDTO.getExpenseCategoryId() + " not found"));
        if (patchExpenseCategoryDTO.getName() != null) expenseCategory.setName(patchExpenseCategoryDTO.getName());
        return expenseCategoryMapper.toDto(expenseCategoryRepository.save(expenseCategory));
    }

    @Override
    public void deleteExpenseCategory(String name) {
        ExpenseCategory expenseCategory = expenseCategoryRepository.findByName(name).orElseThrow(() -> new NotFoundException("Expense category with name " + name + " not found"));
        expenseCategoryRepository.delete(expenseCategory);
    }
}
