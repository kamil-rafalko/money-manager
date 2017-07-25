package com.corriel.repository;

import com.corriel.application.core.entity.Category;
import com.corriel.application.core.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaCategoryRepository extends JpaGenericRepository<Category, Long> implements CategoryRepository {
    JpaCategoryRepository() {
        super(Category.class);
    }
}