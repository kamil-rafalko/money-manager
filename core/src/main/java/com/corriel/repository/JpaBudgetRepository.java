package com.corriel.repository;

import com.corriel.application.core.entity.Budget;
import com.corriel.application.core.repository.BudgetRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaBudgetRepository extends JpaGenericRepository<Budget, Long> implements BudgetRepository {
    JpaBudgetRepository() {
        super(Budget.class);
    }
}
