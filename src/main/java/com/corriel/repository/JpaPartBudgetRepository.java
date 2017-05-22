package com.corriel.repository;

import com.corriel.application.core.entity.MonthlyBudget;
import com.corriel.application.core.repository.PartBudgetRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaPartBudgetRepository extends JpaGenericRepository<MonthlyBudget, Long> implements PartBudgetRepository {
    JpaPartBudgetRepository() {
        super(MonthlyBudget.class);
    }
}
