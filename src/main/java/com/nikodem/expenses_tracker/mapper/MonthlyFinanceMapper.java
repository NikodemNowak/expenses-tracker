package com.nikodem.expenses_tracker.mapper;

import com.nikodem.expenses_tracker.dto.MonthlyFinanceDTO;
import com.nikodem.expenses_tracker.dto.PostMonthlyFinanceDTO;
import com.nikodem.expenses_tracker.model.MonthlyFinance;

import java.util.List;

public interface MonthlyFinanceMapper {
    MonthlyFinanceDTO toDto(MonthlyFinance monthlyFinance);
    List<MonthlyFinanceDTO> toDto(List<MonthlyFinance> monthlyFinances);
    MonthlyFinance toEntity(PostMonthlyFinanceDTO postMonthlyFinanceDTO);
}


