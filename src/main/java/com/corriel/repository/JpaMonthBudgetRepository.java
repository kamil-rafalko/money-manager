package com.corriel.repository;

import com.corriel.application.core.entity.MonthBudget;
import com.corriel.application.core.repository.MonthBudgetRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaMonthBudgetRepository extends JpaGenericRepository<MonthBudget, Long> implements MonthBudgetRepository {
    JpaMonthBudgetRepository() {
        super(MonthBudget.class);
    }
}
