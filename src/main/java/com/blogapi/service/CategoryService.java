package com.blogapi.service;

import com.blogapi.dto.CategoryRequest;
import com.blogapi.dto.CategoryResponse;
import com.blogapi.model.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);
    void deleteCategory(Long id);
    CategoryResponse mapToResponse(Category category);
}
