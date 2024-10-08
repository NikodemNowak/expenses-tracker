package com.nikodem.expenses_tracker.service;

import com.nikodem.expenses_tracker.dto.MonthlyFinanceDTO;
import com.nikodem.expenses_tracker.dto.PatchMonthlyFinanceDTO;
import com.nikodem.expenses_tracker.dto.PostMonthlyFinanceDTO;

import java.util.List;

public interface MonthlyFinanceService {
    List<MonthlyFinanceDTO> findAll();
    MonthlyFinanceDTO findById(String id);
    MonthlyFinanceDTO addMonthlyFinance(PostMonthlyFinanceDTO postMonthlyFinanceDTO);
    MonthlyFinanceDTO updateMonthlyFinance(PatchMonthlyFinanceDTO patchMonthlyFinanceDTO);
    void setMonthlyFinanceExpired(String id);
}

