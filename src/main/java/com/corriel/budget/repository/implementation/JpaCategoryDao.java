package com.corriel.budget.repository.implementation;

import com.corriel.budget.entity.Category;
import com.corriel.budget.repository.CategoryDao;
import com.corriel.data.repository.JpaGenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCategoryDao extends JpaGenericDao<Category, Long> implements CategoryDao {
    public JpaCategoryDao() {
        super(Category.class);
    }
}