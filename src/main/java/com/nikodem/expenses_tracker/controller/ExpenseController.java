package com.nikodem.expenses_tracker.controller;

import com.nikodem.expenses_tracker.dto.ExpenseDTO;
import com.nikodem.expenses_tracker.dto.PatchExpenseDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseDTO;
import com.nikodem.expenses_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpenses() {
        return ResponseEntity.ok(expenseService.findAll());
        // TODO: add pagination (SUPER DANGEROUS REQUEST)
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable @UUID String expenseId) {
        return ResponseEntity.ok(expenseService.findById(java.util.UUID.fromString(expenseId)));
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@Valid @RequestBody PostExpenseDTO postExpenseDTO) {
        return new ResponseEntity<>(expenseService.addExpense(postExpenseDTO), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ExpenseDTO> updateExpense(@Valid @RequestBody PatchExpenseDTO patchExpenseDTO) {
        return ResponseEntity.ok(expenseService.updateExpense(patchExpenseDTO));
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable @UUID String expenseId) {
        expenseService.deleteExpense(java.util.UUID.fromString(expenseId));
        return ResponseEntity.ok(null);
    }
}
