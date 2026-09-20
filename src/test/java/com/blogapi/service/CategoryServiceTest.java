// Category Services

package com.blogapi.service;

import com.blogapi.dto.CategoryRequest;
import com.blogapi.dto.CategoryResponse;
import com.blogapi.exception.BadRequestException;
import com.blogapi.model.entity.Category;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("Technology")
                .description("All things tech")
                .posts(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("Should return all categories")
    void testGetAllCategories() {
        given(categoryRepository.findAll()).willReturn(List.of(category));

        List<CategoryResponse> results = categoryService.getAllCategories();

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Technology");
    }

    @Test
    @DisplayName("Should return category by ID")
    void testGetCategoryById() {
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));

        CategoryResponse result = categoryService.getCategoryById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Technology");
    }

    @Test
    @DisplayName("Should create category successfully")
    void testCreateCategorySuccess() {
        CategoryRequest request = CategoryRequest.builder()
                .name("Programming")
                .description("Coding guides")
                .build();

        given(categoryRepository.existsByNameIgnoreCase("Programming")).willReturn(false);
        given(categoryRepository.save(any(Category.class))).willReturn(
                Category.builder().id(2L).name("Programming").description("Coding guides").build()
        );

        CategoryResponse response = categoryService.createCategory(request);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Programming");
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("Should throw BadRequestException when category name already exists")
    void testCreateCategoryDuplicateName() {
        CategoryRequest request = CategoryRequest.builder()
                .name("Technology")
                .description("Tech")
                .build();

        given(categoryRepository.existsByNameIgnoreCase("Technology")).willReturn(true);

        assertThatThrownBy(() -> categoryService.createCategory(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    @DisplayName("Should delete category")
    void testDeleteCategory() {
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));

        categoryService.deleteCategory(1L);

        verify(categoryRepository).delete(category);
    }
}
