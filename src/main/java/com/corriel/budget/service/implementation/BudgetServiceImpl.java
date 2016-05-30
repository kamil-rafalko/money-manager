package com.corriel.budget.service.implementation;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.repository.BudgetDao;
import com.corriel.budget.service.BudgetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class BudgetServiceImpl implements BudgetService {

    @Inject
    private BudgetDao budgetDao;

    @Override
    public List<Budget> findUsersBudgets(String username) {
        return budgetDao.findUsersBudgets(username);
    }
}
