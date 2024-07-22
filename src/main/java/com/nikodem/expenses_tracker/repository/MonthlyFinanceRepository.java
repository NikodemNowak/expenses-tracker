package com.nikodem.expenses_tracker.repository;

import com.nikodem.expenses_tracker.model.MonthlyFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MonthlyFinanceRepository extends JpaRepository<MonthlyFinance, UUID> {}
