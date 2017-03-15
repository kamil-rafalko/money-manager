package com.corriel.budget.service;

import com.corriel.budget.entity.TransactionCategory;
import com.corriel.budget.repository.CategoryDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {

    private final CategoryDao categoryDao;

    public CategoryService(final CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }


    public void create(TransactionCategory category) {
        categoryDao.create(category);
    }
}