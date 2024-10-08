package com.nikodem.expenses_tracker.repository;

import com.nikodem.expenses_tracker.model.MonthlyFinance;
import com.nikodem.expenses_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MonthlyFinanceRepository extends JpaRepository<MonthlyFinance, UUID> {
    Optional<List<MonthlyFinance>> findAllByExpiredIsFalse();
    Optional<MonthlyFinance> findByIdAndExpiredIsFalse(UUID id);
    Optional<MonthlyFinance> findByUserAndMonthAndYearAndExpiredIsFalse(User user, Month month, int year);
}
