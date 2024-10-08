package com.nikodem.expenses_tracker.controller;

import com.nikodem.expenses_tracker.ApiMessage;
import com.nikodem.expenses_tracker.dto.ExpenseDTO;
import com.nikodem.expenses_tracker.dto.PatchExpenseDTO;
import com.nikodem.expenses_tracker.dto.PostExpenseDTO;
import com.nikodem.expenses_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpenses() {
        return ResponseEntity.ok(expenseService.findAll());
        // TODO: add pagination
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> getExpense(
            @PathVariable
            @org.hibernate.validator.constraints.UUID
            String expenseId
    ) {
        return ResponseEntity.ok(expenseService.findById(UUID.fromString(expenseId)));
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
    public ResponseEntity<ApiMessage> deleteExpense(
            @PathVariable
            @org.hibernate.validator.constraints.UUID
            String expenseId
    ) {
        expenseService.deleteExpense(UUID.fromString(expenseId));
        return ResponseEntity.ok(new ApiMessage("Expense successfully deleted"));
    }
}
