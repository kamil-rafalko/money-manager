package com.corriel.budget.repository.implementation;

import com.corriel.budget.entity.MonthlyBudget;
import com.corriel.budget.repository.PartBudgetDao;
import com.corriel.data.repository.JpaGenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPartBudgetDao extends JpaGenericDao<MonthlyBudget, Long> implements PartBudgetDao {
    public JpaPartBudgetDao() {
        super(MonthlyBudget.class);
    }
}
