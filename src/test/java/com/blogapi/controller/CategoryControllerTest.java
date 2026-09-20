package com.blogapi.controller;

import com.blogapi.dto.CategoryRequest;
import com.blogapi.dto.CategoryResponse;
import com.blogapi.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/categories - Should return all categories")
    void testGetAllCategories() throws Exception {
        CategoryResponse cat = CategoryResponse.builder()
                .id(1L)
                .name("Technology")
                .description("Tech related")
                .postCount(5)
                .build();

        given(categoryService.getAllCategories()).willReturn(List.of(cat));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Technology"))
                .andExpect(jsonPath("$[0].postCount").value(5));
    }

    @Test
    @DisplayName("POST /api/categories - Should create new category")
    void testCreateCategory() throws Exception {
        CategoryRequest request = CategoryRequest.builder()
                .name("AI & ML")
                .description("Artificial Intelligence")
                .build();

        CategoryResponse response = CategoryResponse.builder()
                .id(2L)
                .name("AI & ML")
                .description("Artificial Intelligence")
                .postCount(0)
                .build();

        given(categoryService.createCategory(any(CategoryRequest.class))).willReturn(response);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("AI & ML"));
    }
}
