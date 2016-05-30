package com.corriel.budget.repository;

import com.corriel.budget.entity.Budget;
import com.corriel.data.repository.GenericDao;

import java.util.List;

public interface BudgetDao extends GenericDao<Budget, Long> {
    List<Budget> findUsersBudgets(String username);
}
