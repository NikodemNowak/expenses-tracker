package com.nikodem.expenses_tracker.controller;

import com.nikodem.expenses_tracker.dto.ExpenseCategoryDTO;
import com.nikodem.expenses_tracker.dto.PatchExpenseCategoryDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseCategoryDTO;
import com.nikodem.expenses_tracker.service.ExpenseCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense/category")
public class ExpenseCategoryController {
    private final ExpenseCategoryService expenseCategoryService;

    @Autowired
    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseCategoryDTO>> getAll() {
        return ResponseEntity.ok(expenseCategoryService.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<ExpenseCategoryDTO> getByName(@PathVariable String name) {
        return ResponseEntity.ok(expenseCategoryService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<ExpenseCategoryDTO> create(@RequestBody @Valid PostExpenseCategoryDTO postExpenseCategoryDTO) {
        return new ResponseEntity<>(expenseCategoryService.addExpenseCategory(postExpenseCategoryDTO), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ExpenseCategoryDTO> update(@RequestBody @Valid PatchExpenseCategoryDTO patchExpenseCategoryDTO) {
        return ResponseEntity.ok(expenseCategoryService.updateExpenseCategory(patchExpenseCategoryDTO));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        expenseCategoryService.deleteExpenseCategory(name);
        return ResponseEntity.ok(null);
    }
}
