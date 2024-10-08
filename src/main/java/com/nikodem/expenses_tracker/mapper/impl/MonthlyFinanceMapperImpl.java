package com.nikodem.expenses_tracker.mapper.impl;

import com.nikodem.expenses_tracker.dto.MonthlyFinanceDTO;
import com.nikodem.expenses_tracker.dto.PostMonthlyFinanceDTO;
import com.nikodem.expenses_tracker.exception.NotFoundException;
import com.nikodem.expenses_tracker.mapper.MonthlyFinanceMapper;
import com.nikodem.expenses_tracker.model.MonthlyFinance;
import com.nikodem.expenses_tracker.model.User;
import com.nikodem.expenses_tracker.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class  MonthlyFinanceMapperImpl implements MonthlyFinanceMapper {

    private final UserRepository userRepository;

    public MonthlyFinanceMapperImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MonthlyFinanceDTO toDto(MonthlyFinance monthlyFinance) {
        if (monthlyFinance == null) return null;
        return new MonthlyFinanceDTO(
                monthlyFinance.getId(),
                monthlyFinance.getUser().getId(),
                monthlyFinance.getMonth().toString(),
                monthlyFinance.getYear(),
                monthlyFinance.getIncome(),
                monthlyFinance.getExpensesTotal()
        );
    }

    @Override
    public List<MonthlyFinanceDTO> toDto(List<MonthlyFinance> monthlyFinances) {
        List<MonthlyFinanceDTO> monthlyFinanceDTOs = new ArrayList<>();
        for (MonthlyFinance monthlyFinance : monthlyFinances) {
            monthlyFinanceDTOs.add(toDto(monthlyFinance));
        }
        return monthlyFinanceDTOs;
    }

    @Override
    public MonthlyFinance toEntity(PostMonthlyFinanceDTO postMonthlyFinanceDTO) {
        if (postMonthlyFinanceDTO == null) return null;
        User user = userRepository
                .findById(UUID.fromString(postMonthlyFinanceDTO.getUserId()))
                .orElseThrow(() -> new NotFoundException(
                        "User with id " + postMonthlyFinanceDTO.getUserId() + " not found"
                ));
        Month month;
        try {
            month = Month.valueOf(postMonthlyFinanceDTO.getMonth().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Month " + postMonthlyFinanceDTO.getMonth() + " not found");
        }
        return new MonthlyFinance(
                user,
                month,
                postMonthlyFinanceDTO.getYear(),
                postMonthlyFinanceDTO.getIncome()
        );
    }
}
