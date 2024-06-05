package com.example.webstore.service;

import com.example.webstore.model.Category;
import com.example.webstore.repository.CategoryRepository;
import com.example.webstore.service.Impl.ImplCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ImplCategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> updateCategory(Long id, Category category) {
        Optional<Category> existingCategoryOptional = getCategoryById(id);
        if (existingCategoryOptional.isPresent()) {
            Category existingCategory = existingCategoryOptional.get();
            existingCategory.setCategoryName(category.getCategoryName());
            existingCategory.setProducts(category.getProducts());
            return Optional.of(categoryRepository.save(existingCategory));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteCategory(Long id) {
        Optional<Category> categoryOptional = getCategoryById(id);
        if (categoryOptional.isPresent()) {
            categoryRepository.delete(categoryOptional.get());
            return true;
        } else {
            return false;
        }
    }
}
