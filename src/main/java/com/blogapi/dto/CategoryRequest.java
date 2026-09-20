package com.blogapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for creating or updating a category")
public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    @Schema(description = "Unique category name", example = "Technology")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Schema(description = "Category description", example = "All things related to software, gadgets, and tech")
    private String description;

    public CategoryRequest() {
    }

    public CategoryRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static CategoryRequestBuilder builder() {
        return new CategoryRequestBuilder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class CategoryRequestBuilder {
        private String name;
        private String description;

        public CategoryRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CategoryRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CategoryRequest build() {
            return new CategoryRequest(name, description);
        }
    }
}
