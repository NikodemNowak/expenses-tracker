package com.nikodem.expenses_tracker.controller;

import com.nikodem.expenses_tracker.ApiMessage;
import com.nikodem.expenses_tracker.dto.MonthlyFinanceDTO;
import com.nikodem.expenses_tracker.dto.PatchMonthlyFinanceDTO;
import com.nikodem.expenses_tracker.dto.PostMonthlyFinanceDTO;
import com.nikodem.expenses_tracker.service.MonthlyFinanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monthly-finance")
public class MonthlyFinanceController {

    private final MonthlyFinanceService monthlyFinanceService;

    public MonthlyFinanceController(MonthlyFinanceService monthlyFinanceService) {
        this.monthlyFinanceService = monthlyFinanceService;
    }

    @GetMapping
    public ResponseEntity<List<MonthlyFinanceDTO>> getAllMonthlyFinances() {
        return ResponseEntity.ok(monthlyFinanceService.findAll());
    }

    @GetMapping("/{monthlyFinanceId}")
    public ResponseEntity<MonthlyFinanceDTO> getMonthlyFinance(@PathVariable String monthlyFinanceId) {
        return ResponseEntity.ok(monthlyFinanceService.findById(monthlyFinanceId));
    }

    @PostMapping
    public ResponseEntity<MonthlyFinanceDTO> createMonthlyFinance(@RequestBody @Valid PostMonthlyFinanceDTO postMonthlyFinanceDTO) {
        return new ResponseEntity<>(monthlyFinanceService.addMonthlyFinance(postMonthlyFinanceDTO), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<MonthlyFinanceDTO> updateMonthlyFinance(@RequestBody @Valid PatchMonthlyFinanceDTO patchMonthlyFinanceDTO) {
        return ResponseEntity.ok(monthlyFinanceService.updateMonthlyFinance(patchMonthlyFinanceDTO));
    }

    @DeleteMapping("/{monthlyFinanceId}")
    public ResponseEntity<ApiMessage> deleteMonthlyFinance(@PathVariable String monthlyFinanceId) {
        monthlyFinanceService.setMonthlyFinanceExpired(monthlyFinanceId);
        return ResponseEntity.ok(new ApiMessage("Monthly finance successfully deleted"));
    }
}
