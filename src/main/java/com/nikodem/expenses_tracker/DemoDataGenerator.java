package com.nikodem.expenses_tracker;

import com.nikodem.expenses_tracker.model.*;
import com.nikodem.expenses_tracker.repository.ExpenseCategoryRepository;
import com.nikodem.expenses_tracker.repository.ExpenseRepository;
import com.nikodem.expenses_tracker.repository.MonthlyFinanceRepository;
import com.nikodem.expenses_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DemoDataGenerator implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final MonthlyFinanceRepository monthlyFinanceRepository;

    @Autowired
    public DemoDataGenerator(UserRepository userRepository, ExpenseRepository expenseRepository, ExpenseCategoryRepository expenseCategoryRepository, MonthlyFinanceRepository monthlyFinanceRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.monthlyFinanceRepository = monthlyFinanceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User alex = new User("alex@gmail.com", "Alex");
        userRepository.save(alex);
        ExpenseCategory groceries = new ExpenseCategory("groceries");
        ExpenseCategory clothes = new ExpenseCategory("clothes");
        expenseCategoryRepository.save(groceries);
        expenseCategoryRepository.save(clothes);
        MonthlyFinance monthlyFinance = new MonthlyFinance(alex, Month.JULY, 2024, BigDecimal.valueOf(4500));
        monthlyFinanceRepository.save(monthlyFinance);
        expenseRepository.save(new Expense("Groceries", "Lidl shopping", BigDecimal.valueOf(120.99), groceries, LocalDate.parse("2024-07-19"), monthlyFinance));
        expenseRepository.save(new Expense("Shoes", "Nike Jordan 1 High", BigDecimal.valueOf(799), clothes, LocalDate.parse("2024-07-19"), monthlyFinance));
    }
}
