package com.corriel.application.core.budget;

import com.corriel.application.core.annotations.ApplicationService;
import com.corriel.application.core.entity.Category;
import com.corriel.application.core.repository.CategoryRepository;

@ApplicationService
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(final CategoryRepository repository) {
        this.repository = repository;
    }

    void create(Category category) {
        repository.create(category);
    }
}