package com.nikodem.expenses_tracker.service.impl;

import com.nikodem.expenses_tracker.dto.MonthlyFinanceDTO;
import com.nikodem.expenses_tracker.dto.PatchMonthlyFinanceDTO;
import com.nikodem.expenses_tracker.dto.PostMonthlyFinanceDTO;
import com.nikodem.expenses_tracker.exception.NotFoundException;
import com.nikodem.expenses_tracker.mapper.MonthlyFinanceMapper;
import com.nikodem.expenses_tracker.model.MonthlyFinance;
import com.nikodem.expenses_tracker.repository.MonthlyFinanceRepository;
import com.nikodem.expenses_tracker.service.MonthlyFinanceService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MonthlyFinanceServiceImpl implements MonthlyFinanceService {

    private final MonthlyFinanceRepository monthlyFinanceRepository;
    private final MonthlyFinanceMapper monthlyFinanceMapper;

    public MonthlyFinanceServiceImpl(
            MonthlyFinanceRepository monthlyFinanceRepository,
            MonthlyFinanceMapper monthlyFinanceMapper
    ) {
        this.monthlyFinanceRepository = monthlyFinanceRepository;
        this.monthlyFinanceMapper = monthlyFinanceMapper;
    }

    @Override
    public List<MonthlyFinanceDTO> findAll() {
        return monthlyFinanceMapper.toDto(
                monthlyFinanceRepository
                        .findAllByExpiredIsFalse()
                        .orElseThrow(() -> new NotFoundException(
                                "Monthly finances not found"
                        ))
        );
    }

    @Override
    public MonthlyFinanceDTO findById(String id) {
        if (id.isBlank()) return null;
        return monthlyFinanceMapper.toDto(
                monthlyFinanceRepository
                        .findByIdAndExpiredIsFalse(UUID.fromString(id))
                        .orElseThrow(() -> new NotFoundException(
                                "Monthly finance with id " + id + " not found"
                        ))
        );
    }

    @Override
    @Transactional
    public MonthlyFinanceDTO addMonthlyFinance(PostMonthlyFinanceDTO postMonthlyFinanceDTO) {
        return monthlyFinanceMapper.toDto(
                monthlyFinanceRepository.save(
                        monthlyFinanceMapper.toEntity(postMonthlyFinanceDTO
                        )
                )
        );
    }

    @Override
    @Transactional
    public MonthlyFinanceDTO updateMonthlyFinance(PatchMonthlyFinanceDTO patchMonthlyFinanceDTO) {
        MonthlyFinance monthlyFinance = monthlyFinanceRepository
                .findByIdAndExpiredIsFalse(patchMonthlyFinanceDTO.getMonthlyFinanceId())
                .orElseThrow(() -> new NotFoundException(
                        "Monthly finance with id " + patchMonthlyFinanceDTO.getMonthlyFinanceId() + " not found"
                ));
        if (patchMonthlyFinanceDTO.getIncome() != null)
            monthlyFinance.setIncome(patchMonthlyFinanceDTO.getIncome());
        return monthlyFinanceMapper.toDto(
                monthlyFinanceRepository.save(monthlyFinance)
        );
    }

    @Override
    public void setMonthlyFinanceExpired(String id) {
        MonthlyFinance monthlyFinance = monthlyFinanceRepository
                .findByIdAndExpiredIsFalse(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(
                        "Monthly finance with id " + id + " not found"
                ));
        monthlyFinance.setExpired(true);
        monthlyFinanceRepository.save(monthlyFinance);
    }
}

