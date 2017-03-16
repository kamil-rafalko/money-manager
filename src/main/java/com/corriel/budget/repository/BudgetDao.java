package com.corriel.budget.repository;

import com.corriel.budget.entity.Budget;
import com.corriel.data.repository.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class BudgetDao extends GenericDao<Budget, Long> {
    public BudgetDao() {
        super(Budget.class);
    }
}
