package com.blogapi.service.impl;

import com.blogapi.dto.CategoryRequest;
import com.blogapi.dto.CategoryResponse;
import com.blogapi.exception.BadRequestException;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.model.entity.Category;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        log.info("Fetching all categories");
        return categoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        log.info("Fetching category with id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return mapToResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        log.info("Creating category with name: {}", categoryRequest.getName());
        if (categoryRepository.existsByNameIgnoreCase(categoryRequest.getName().trim())) {
            throw new BadRequestException("Category with name '" + categoryRequest.getName() + "' already exists");
        }

        Category category = Category.builder()
                .name(categoryRequest.getName().trim())
                .description(categoryRequest.getDescription())
                .build();

        Category savedCategory = categoryRepository.save(category);
        log.info("Created category successfully with id: {}", savedCategory.getId());
        return mapToResponse(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        log.info("Updating category id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        String newName = categoryRequest.getName().trim();
        categoryRepository.findByNameIgnoreCase(newName)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new BadRequestException("Category with name '" + newName + "' already exists");
                    }
                });

        category.setName(newName);
        category.setDescription(categoryRequest.getDescription());

        Category updatedCategory = categoryRepository.save(category);
        log.info("Updated category successfully with id: {}", updatedCategory.getId());
        return mapToResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        log.info("Deleting category id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
        log.info("Deleted category successfully with id: {}", id);
    }

    @Override
    public CategoryResponse mapToResponse(Category category) {
        int postCount = category.getPosts() != null ? category.getPosts().size() : 0;
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .postCount(postCount)
                .build();
    }
}
