package com.corriel.budget.repository;

import com.corriel.budget.entity.Category;
import com.corriel.data.repository.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDao extends GenericDao<Category, Long> {
    public CategoryDao() {
        super(Category.class);
    }
}