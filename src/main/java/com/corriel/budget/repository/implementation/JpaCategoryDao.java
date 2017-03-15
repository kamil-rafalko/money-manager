package com.corriel.budget.repository.implementation;

import com.corriel.budget.entity.TransactionCategory;
import com.corriel.budget.repository.CategoryDao;
import com.corriel.data.repository.JpaGenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCategoryDao extends JpaGenericDao<TransactionCategory, Long> implements CategoryDao {
    public JpaCategoryDao() {
        super(TransactionCategory.class);
    }
}