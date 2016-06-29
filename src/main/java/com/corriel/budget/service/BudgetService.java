package com.corriel.budget.service;

import com.corriel.budget.entity.Budget;

import java.util.List;

public interface BudgetService {

    List<Budget> findUsersBudgets(String username);
    Budget find(long id);
}
