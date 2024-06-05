package com.example.webstore.service.Impl;

import com.example.webstore.model.Category;

import java.util.List;
import java.util.Optional;

public interface ImplCategoryService {
    Category createCategory(Category category);
    Optional<Category> getCategoryById(Long id);
    List<Category> getAllCategories();
    Optional<Category> updateCategory(Long id, Category category);
    boolean deleteCategory(Long id);
}
