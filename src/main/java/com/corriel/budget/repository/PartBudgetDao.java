package com.corriel.budget.repository;

import com.corriel.budget.entity.MonthlyBudget;
import com.corriel.data.repository.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class PartBudgetDao extends GenericDao<MonthlyBudget, Long> {
    public PartBudgetDao() {
        super(MonthlyBudget.class);
    }
}
