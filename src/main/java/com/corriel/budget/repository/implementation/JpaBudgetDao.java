package com.corriel.budget.repository.implementation;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.repository.BudgetDao;
import com.corriel.data.repository.JpaGenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaBudgetDao extends JpaGenericDao<Budget, Long> implements BudgetDao {
    public JpaBudgetDao() {
        super(Budget.class);
    }
}
