package com.blogapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body representing a category")
public class CategoryResponse {

    @Schema(description = "Unique category ID", example = "1")
    private Long id;

    @Schema(description = "Category name", example = "Technology")
    private String name;

    @Schema(description = "Category description", example = "All things related to software, gadgets, and tech")
    private String description;

    @Schema(description = "Number of posts in this category", example = "5")
    private Integer postCount;

    public CategoryResponse() {
    }

    public CategoryResponse(Long id, String name, String description, Integer postCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.postCount = postCount;
    }

    public static CategoryResponseBuilder builder() {
        return new CategoryResponseBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public static class CategoryResponseBuilder {
        private Long id;
        private String name;
        private String description;
        private Integer postCount;

        public CategoryResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CategoryResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CategoryResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CategoryResponseBuilder postCount(Integer postCount) {
            this.postCount = postCount;
            return this;
        }

        public CategoryResponse build() {
            return new CategoryResponse(id, name, description, postCount);
        }
    }
}
